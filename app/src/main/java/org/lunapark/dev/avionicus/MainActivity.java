package org.lunapark.dev.avionicus;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.lunapark.dev.avionicus.helpers.Avionikus;
import org.lunapark.dev.avionicus.helpers.EventListener;
import org.lunapark.dev.avionicus.helpers.GetData;
import org.lunapark.dev.avionicus.helpers.Segment;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements EventListener, View.OnClickListener {

    private String TAG = "Avionicus test app";
    private String linkStart = "http://avionicus.com/android/track_v0649.php?avkey=1M1TE9oeWTDK6gFME9JYWXqpAGc%3D&hash=58ecdea2a91f32aa4c9a1d2ea010adcf2348166a04&track_id=";
    private static String trackId = "36131"; // Default value
    private String linkEnd = "&user_id=22";
    private GetData getData;
    private IMapController mapController;
    private MapView map;
    private EditText etTrackId, etSpeedLo, etSpeedMid, etSpeedHi;
    private int speedLow, speedMiddle, speedHigh; // Speed values

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();
        //important! set your user agent to prevent getting banned from the osm servers
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
        setContentView(R.layout.activity_main);

        map = (MapView) findViewById(R.id.mvMain);
        map.setTileSource(TileSourceFactory.MAPNIK);

        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        mapController = map.getController();
        mapController.setZoom(15);

        etTrackId = (EditText) findViewById(R.id.etTrackId);
        etTrackId.setText(String.valueOf(trackId));

        etSpeedLo = (EditText) findViewById(R.id.etSpeedLow);
        etSpeedMid = (EditText) findViewById(R.id.etSpeedMiddle);
        etSpeedHi = (EditText) findViewById(R.id.etSpeedHigh);

        // Default
        speedLow = 5;
        speedMiddle = 10;
        speedHigh = 20;

        refreshSpeedEt();

        Button btnGo = (Button) findViewById(R.id.btnGo);
        btnGo.setOnClickListener(this);
        getData = new GetData(context, this);
    }

    private String buildLink(String id) {
        return linkStart + id + linkEnd;
    }

    @Override
    protected void onPause() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Configuration.getInstance().save(this, prefs);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        getData.get(buildLink(trackId));
    }

    @Override
    protected void onDestroy() {
        getData.dispose();
        super.onDestroy();
    }

    @Override
    public void onEvent(Avionikus avionikus) {
        // Строим путь
        ArrayList<Polyline> path = buildPath(avionikus);

        // Рисуем новый путь на карте
        for (int i = 0; i < path.size(); i++) {
            map.getOverlays().add(path.get(i));
        }
        map.invalidate();
    }

    @Override
    public void onFailure() {
        Log.e(TAG, "shit goes down");
    }

    private ArrayList<Polyline> buildPath(Avionikus avionikus) {
        ArrayList<Polyline> polylines = new ArrayList<>();
        List<List<Double>> vectors = avionikus.getAPoints();
        ArrayList<Segment> segments = new ArrayList<>();

        for (int i = 0; i < vectors.size(); i++) {
            List<Double> points = vectors.get(i);
            for (int j = 0; j < points.size(); j++) {
                GeoPoint geoPoint = new GeoPoint(points.get(0), points.get(1));
                double speed = points.get(5);
                int color = getResources().getColor(R.color.col_spd_0);
                if (speed > speedLow) color = getResources().getColor(R.color.col_spd_low);
                if (speed > speedMiddle) color = getResources().getColor(R.color.col_spd_middle);
                if (speed > speedHigh) color = getResources().getColor(R.color.col_spd_high);

                Segment segment = new Segment();
                segment.color = color;
                segment.geoPoint = geoPoint;

                segments.add(segment);
            }
        }

        // Go to the 1st point
        mapController.animateTo(segments.get(0).geoPoint);

        // Build color path
        int color = segments.get(0).color;
        Polyline polyline = getPolyline(color);
        ArrayList<GeoPoint> geoPoints = new ArrayList<>();
        polylines.add(polyline);

        for (int i = 1; i < segments.size(); i++) {
            GeoPoint geoPoint = segments.get(i).geoPoint;
            geoPoints.add(geoPoint);
            if (color != segments.get(i).color) {
                polyline.setPoints(geoPoints);
                color = segments.get(i).color;
                polyline = getPolyline(color);
                geoPoints = new ArrayList<>();
                polylines.add(polyline);
            }
        }
        polyline.setPoints(geoPoints);
        polylines.add(polyline);
        return polylines;
    }

    private Polyline getPolyline(int color) {
        Polyline polyline = new Polyline();
        polyline.setWidth(5);
        polyline.setColor(color);
        return polyline;
    }

    @Override
    public void onClick(View v) {
        correctSpeedValues();
        // Удаляем предыдущие пути
        map.getOverlays().clear();
        // Запрос новых данных
        trackId = String.valueOf(etTrackId.getText());
        getData.get(buildLink(trackId));
    }

    private void refreshSpeedEt() {
        etSpeedLo.setText(String.valueOf(speedLow));
        etSpeedMid.setText(String.valueOf(speedMiddle));
        etSpeedHi.setText(String.valueOf(speedHigh));
    }

    private void correctSpeedValues() {
        int spdLo = Integer.parseInt(etSpeedLo.getText().toString());
        int spdMd = Integer.parseInt(etSpeedMid.getText().toString());
        int spdHi = Integer.parseInt(etSpeedHi.getText().toString());

        if (spdLo <= 0) spdLo = 1;
        if (spdMd <= spdLo) spdMd = spdLo + 1;
        if (spdHi <= spdMd) spdHi = spdMd + 1;

        speedLow = spdLo;
        speedMiddle = spdMd;
        speedHigh = spdHi;

        refreshSpeedEt();
    }
}
