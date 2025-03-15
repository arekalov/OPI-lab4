package com.arekalov.jsfgraph.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AreaCheckerTest {

    @Test
    public void testIsInArea_Rectangle_True() {
        assertTrue(AreaChecker.isInArea(0.5, -0.5, 2.0), "Point (0.5, -0.5) should be inside the rectangle.");
    }

    @Test
    public void testIsInArea_Rectangle_False() {
        assertFalse(AreaChecker.isInArea(1.5, -2.5, 2.0), "Point (1.5, -2.5) should be outside the rectangle.");
    }

    @Test
    public void testIsInArea_Semicircle_True() {
        assertTrue(AreaChecker.isInArea(-1.0, 1.0, 2.0), "Point (-1.0, 1.0) should be inside the semicircle.");
    }

    @Test
    public void testIsInArea_Semicircle_False() {
        assertFalse(AreaChecker.isInArea(-3.0, 3.0, 2.0), "Point (-3.0, 3.0) should be outside the semicircle.");
    }

    @Test
    public void testIsInArea_Triangle_False() {
        assertFalse(AreaChecker.isInArea(-3.0, -3.0, 2.0), "Point (-3.0, -3.0) should be outside the triangle.");
    }

    @Test
    public void testIsInArea_RadiusZero() {
        assertFalse(AreaChecker.isInArea(0.0, 0.0, 0.0), "All points should be outside when radius r is 0.");
    }
}
