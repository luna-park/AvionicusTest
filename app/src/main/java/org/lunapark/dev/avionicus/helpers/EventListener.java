package org.lunapark.dev.avionicus.helpers;

/**
 * Created by znak on 01.04.2017.
 */

public interface EventListener {
    void onEvent(Avionikus avionikus);
    void onFailure();
}
