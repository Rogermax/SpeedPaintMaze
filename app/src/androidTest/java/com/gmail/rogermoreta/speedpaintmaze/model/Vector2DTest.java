package com.gmail.rogermoreta.speedpaintmaze.model;

import android.os.Build;

import junit.framework.TestCase;

public class Vector2DTest extends TestCase {

    Vector2D v1 = new Vector2D(1f, 0f);
    Vector2D v2 = new Vector2D(0f, 1f);
    Vector2D v3 = new Vector2D(1f, 1f);
    Vector2D v4 = new Vector2D(3f, 4f);
    Vector2D v5 = new Vector2D(0f, 0f);

    public void setUp() throws Exception {
        super.setUp();
    }

    public void testTodoUnPoco() {
        //Suma
        v1.suma(v2);
        Vector2D suma = Vector2D.suma(v1, v2);
        assertEquals(1f, suma.m_x, 0.0001);
        assertEquals(2f, suma.m_y, 0.0001);

        //Nulo
        v1 = new Vector2D();
        assertEquals(true, v1.esNulo());

        //Resta e inversion
        v1.resta(v3);
        v1.invertir();
        v1.resta(v2);
        assertEquals(1f, v1.m_x, 0001);
        assertEquals(0f, v1.m_y, 0001);

        //Multiplicar por escalar y normalizar
        v1.multipicaPorEscalar(2f);
        assertEquals(2f, v1.m_x, 0001);
        assertEquals(0f, v1.m_y, 0001);
        v1.normaliza();
        assertEquals(1f, v1.m_x, 0001);
        assertEquals(0f, v1.m_y, 0001);

        //Angulos
        assertEquals(45f, Vector2D.anguloEnGradosEntre(v1,v3), 0.0001);
        assertEquals(90f, Vector2D.anguloEnGradosEntre(v1, v2), 0.0001);

        //perpendicularidades
        assertEquals(0f, Vector2D.productoEscalar(v1, v2), 0.0001);
        assertEquals(true, Vector2D.sonPerpendiculares(v1, v2));
        assertEquals(false, Vector2D.sonPerpendiculares(v1, v3));

    }
}