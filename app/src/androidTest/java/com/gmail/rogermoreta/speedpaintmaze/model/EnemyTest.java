package com.gmail.rogermoreta.speedpaintmaze.model;

import junit.framework.TestCase;

public class EnemyTest extends TestCase {
    Enemy enemy;

    public void setUp() throws Exception {
        super.setUp();
        enemy = new Enemy();
        enemy.init(0,0,100f,1f);
    }

    public void tearDown() throws Exception {
        enemy = null;
    }

    public void testLogicStill() throws Exception {
        enemy.logic(40l);
        assertEquals(0f, enemy.m_posicion.m_x);
        assertEquals(0f, enemy.m_posicion.m_y);
    }

    public void testLogicMove() throws Exception {
        enemy.asignarMoveTarget(10f, 0f);
        enemy.logic(20l);
        assertEquals(1f, enemy.m_posicion.m_x);
        assertEquals(0f, enemy.m_posicion.m_y);
        enemy.logic(15l);
        assertEquals(1.75f, enemy.m_posicion.m_x);
        assertEquals(0f, enemy.m_posicion.m_y);
        enemy.logic(60l);
        assertEquals(4.75f, enemy.m_posicion.m_x);
        assertEquals(0f, enemy.m_posicion.m_y);
    }

    public void testLogicTargetTaken() throws Exception {
        enemy.asignarMoveTarget(10f, 0f);
        enemy.logic(20l);
        assertEquals(1f, enemy.m_posicion.m_x);
        assertEquals(0f, enemy.m_posicion.m_y);
        enemy.logic(15l);
        assertEquals(1.75f, enemy.m_posicion.m_x);
        assertEquals(0f, enemy.m_posicion.m_y);
        enemy.logic(60l);
        assertEquals(4.75f, enemy.m_posicion.m_x);
        assertEquals(0f, enemy.m_posicion.m_y);
        enemy.logic(105);
        assertEquals(10f, enemy.m_posicion.m_x);
        assertEquals(0f, enemy.m_posicion.m_y);
        enemy.logic(105);
        assertEquals(10f, enemy.m_posicion.m_x);
        assertEquals(0f, enemy.m_posicion.m_y);
        enemy.logic(105);
        assertEquals(10f, enemy.m_posicion.m_x);
        assertEquals(0f, enemy.m_posicion.m_y);
    }

    public void testLogicStop() throws Exception {
        enemy.asignarMoveTarget(10f, 10f);
        enemy.logic(20l);
        assertEquals(0.707107f, enemy.m_posicion.m_x, 0.001f);
        assertEquals(0.707107f, enemy.m_posicion.m_y, 0.001f);
        enemy.quitarMoveTarget();
        enemy.logic(60l);
        assertEquals(0.707107f, enemy.m_posicion.m_x, 0.001f);
        assertEquals(0.707107f, enemy.m_posicion.m_y, 0.001f);
    }

    public void testDie() throws Exception {
        enemy.receiveDamage(33);
        enemy.logic(15);
        assertEquals(67f, enemy.m_actualLife);
        assertEquals(true, enemy.m_isAlive);

        enemy.receiveDamage(33);
        enemy.logic(15);
        assertEquals(34f, enemy.m_actualLife);
        assertEquals(true, enemy.m_isAlive);

        enemy.receiveDamage(33);
        enemy.logic(15);
        assertEquals(1f, enemy.m_actualLife);
        assertEquals(true, enemy.m_isAlive);

        enemy.receiveDamage(33);
        enemy.logic(15);
        assertEquals(-32f, enemy.m_actualLife);
        assertEquals(true, enemy.m_isAlive);


        enemy.logic(1000);
        assertEquals(-32f, enemy.m_actualLife);
        assertEquals(false, enemy.m_isAlive);
    }

    public void testLogicAttack() throws Exception {
        enemy.asignarAttackTarget(200f, 200f);
        enemy.m_attackDistance = 282f;

        //No ataca porque no llega
        enemy.logic(20l);
        assertEquals(0f, enemy.m_posicion.m_x);
        assertEquals(0f, enemy.m_posicion.m_y);
        enemy.asignarMoveTarget(200f, 200f);

        //Primero se mueve, luego ataca, por tanto ahora ya llega
        enemy.logic(20l);
        assertEquals(0.707107f, enemy.m_posicion.m_x, 0.001f);
        assertEquals(0.707107f, enemy.m_posicion.m_y, 0.001f);
        enemy.quitarMoveTarget();

        //Ahora no puede atacar porque lo ha hecho hace menos de 3000  milisengundos
        enemy.logic(20l);
        assertEquals(0.707107f, enemy.m_posicion.m_x, 0.001f);
        assertEquals(0.707107f, enemy.m_posicion.m_y, 0.001f);

        //Aunque pasen 10segundos, solo dispara 1 vez, sino se complica mucho.
        enemy.logic(10000l);
        assertEquals(0.707107f, enemy.m_posicion.m_x, 0.001f);
        assertEquals(0.707107f, enemy.m_posicion.m_y, 0.001f);


    }
}