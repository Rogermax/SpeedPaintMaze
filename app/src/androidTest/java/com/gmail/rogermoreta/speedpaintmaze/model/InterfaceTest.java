package com.gmail.rogermoreta.speedpaintmaze.model;

import junit.framework.TestCase;

public class InterfaceTest extends TestCase {
    Interface anInterface;

    public void setUp() throws Exception {
        super.setUp();
        anInterface = new Interface(8,0,0,1000,200, false);
        anInterface.logic(100);
    }

    public void tearDown() throws Exception {
        anInterface = null;
    }

    public void testLogic() throws Exception {
        //logica sin que aparezca ni nada.
        anInterface.logic(100);
        anInterface.logic(100);
        assertFalse(anInterface.isActive());
        assertEquals(0, anInterface.stepShowing());

        //Pondremos que aparezca y que passen 198 milisec, no deberia mostrarse del to do.
        anInterface.startShowing();
        anInterface.logic(198);
        assertFalse(anInterface.isActive());
        assertEquals(199, anInterface.stepShowing());

        //ahora hacemos que pasen 2 milisegundos, ya se deberia mostrar y el step deberia quedarse maximizado a 2.
        anInterface.logic(2);
        assertTrue(anInterface.isActive());
        assertEquals(200, anInterface.stepShowing());

        //ahora le volvemos a dar start (nada deberia canviar
        anInterface.startShowing();
        anInterface.logic(1898);
        assertTrue(anInterface.isActive());
        assertEquals(200, anInterface.stepShowing());

        //Ahora le pedimos un retract
        anInterface.startRetracting();
        assertFalse(anInterface.isActive());
        assertEquals(199, anInterface.stepShowing());

        //Ahora hacemos que pase la mitad del tiempo y le lanzamos un start, no deberia moverse el contador
        anInterface.logic(99);
        anInterface.startShowing();
        assertFalse(anInterface.isActive());
        assertEquals(100, anInterface.stepShowing());

        //Ahora hacemos que pase epsilon y retomamos el retracting
        anInterface.logic(1);
        anInterface.startRetracting();
        assertFalse(anInterface.isActive());
        assertEquals(101, anInterface.stepShowing());

        //Ahora hacemos que acabe
        anInterface.logic(3000);
        assertFalse(anInterface.isActive());
        assertEquals(0, anInterface.stepShowing());

    }

    public void testIsSomethingClicked() throws Exception {

    }

    public void testStartShowing() throws Exception {

    }

    public void testStartRetracting() throws Exception {

    }

    public void testStepShowing() throws Exception {

    }

    public void testIsActive() throws Exception {

    }

    public void testHighLight() throws Exception {

    }

    public void testIsClickInside() throws Exception {

    }

    public void testGetSelectedButton() throws Exception {

    }

    public void testDesSeleccionar() throws Exception {

    }
}