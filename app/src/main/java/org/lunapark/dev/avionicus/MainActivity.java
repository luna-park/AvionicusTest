package org.lunapark.dev.avionicus;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

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

public class MainActivity extends Activity implements EventListener {

    private String link = "http://avionicus.com/android/track_v0649.php?avkey=1M1TE9oeWTDK6gFME9JYWXqpAGc%3D&hash=58ecdea2a91f32aa4c9a1d2ea010adcf2348166a04&track_id=36131&user_id=22";
    private GetData getData;
    private IMapController mapController;
    private MapView map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context ctx = getApplicationContext();
        //important! set your user agent to prevent getting banned from the osm servers
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_main);

        map = (MapView) findViewById(R.id.mvMain);
        map.setTileSource(TileSourceFactory.MAPNIK);

        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        mapController = map.getController();
        mapController.setZoom(15);

        getData = new GetData(this);
        getData.get(link);


    }

    @Override
    public void onResume() {
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
    }

    @Override
    protected void onStop() {
        getData.dispose();
        super.onStop();
    }

    @Override
    public void onEvent(Avionikus avionikus) {
        final List<List<Double>> vectors = avionikus.getAPoints();
        List<Double> points = null;
        ArrayList<Segment> segments = new ArrayList<>();

        for (int i = 0; i < vectors.size(); i++) {
            points = vectors.get(i);
            for (int j = 0; j < points.size(); j++) {
                Log.e("Avionikus", i + "." + j + ": ("
                        + points.get(0) + ","
                        + points.get(1) + ") speed: " +
                        +points.get(5)
                );
                GeoPoint geoPoint = new GeoPoint(points.get(0), points.get(1));
                double speed = points.get(5);
                int color = Color.rgb(64, 224, 208);
                if (speed > 5 && speed < 10) color = Color.rgb(0, 128, 0);
                if (speed >= 10 && speed < 20) color = Color.YELLOW;
                if (speed >= 20) color = Color.RED;

                Segment segment = new Segment();
                segment.color = color;
                segment.geoPoint = geoPoint;

                segments.add(segment);
            }
        }
        if (points != null) {
            GeoPoint startPoint = new GeoPoint(points.get(0), points.get(1));
            mapController.animateTo(startPoint);
        }

        ArrayList<Polyline> polylines = new ArrayList<>();

        int color = segments.get(0).color;
        Polyline polyline = getPolyline(color);
        ArrayList<GeoPoint> arrayList = getList();
        polylines.add(polyline);

        for (int i = 1; i < segments.size(); i++) {
            GeoPoint geoPoint = segments.get(i).geoPoint;
            arrayList.add(geoPoint);
            if (color != segments.get(i).color) {
                polyline.setPoints(arrayList);
                color = segments.get(i).color;
                polyline = getPolyline(color);
                arrayList = getList();
                polylines.add(polyline);
            }
        }
        polylines.add(polyline);

        for (int i = 0; i < polylines.size(); i++) {
            map.getOverlays().add(polylines.get(i));
        }

    }

    private Polyline getPolyline(int color) {
        Polyline polyline = new Polyline();
        polyline.setWidth(5);
        polyline.setColor(color);
        return polyline;
    }

    private ArrayList<GeoPoint> getList() {
        return new ArrayList<>();
    }
}
