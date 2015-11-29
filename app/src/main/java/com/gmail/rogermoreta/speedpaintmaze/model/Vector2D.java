package com.gmail.rogermoreta.speedpaintmaze.model;


@SuppressWarnings("unused")
public class Vector2D {
    float m_x;
    float m_y;

    public Vector2D() {
        m_x = 0f;
        m_y = 0f;
    }

    public Vector2D(float x, float y) {
        m_x = x;
        m_y = y;
    }

    public Vector2D(Vector2D v) {
        m_x = v.m_x;
        m_y = v.m_y;
    }

    /**
     * Método que modifica el vector this para convertirlo en su normalizado,
     * i.e. lo convierte en un vector con la misma dirección pero de módulo 1.
     * Lo normal es que después de normalizarlo lo multipliquemos por un escalar
     * (numero) para transformar-lo en un vector del tamaño que deseemos y con
     * la dirección original
     */
    public void normaliza() {
        float length = modulo();
        if (length != 0) {
            m_x /= length;
            m_y /= length;
        }
    }

    /**
     * Método que modifica el vector pasado para convertirlo en su normalizado,
     * i.e. lo convierte en un vector con la misma dirección pero de módulo 1.
     * Lo normal es que después de normalizarlo lo multipliquemos por un escalar
     * (numero) para transformar-lo en un vector del tamaño que deseemos y con
     * la dirección original
     */
    public static void normaliza(Vector2D v) {
        float length = v.modulo();
        if (length != 0) {
            v.m_x /= length;
            v.m_y /= length;
        }
    }


    /**
     * Método que retorna un Vector2D que es el normalizado del this.
     *
     * @return Vector2D normalizado del this.
     */
    public Vector2D normalizado() {
        Vector2D ret = new Vector2D(m_x, m_y);
        ret.normaliza();
        return ret;
    }

    /**
     * Método que retorna un Vector2D que es el normalizado del pasado.
     *
     * @return Vector2D normalizado del pasado.
     */
    public static Vector2D normalizado(Vector2D v) {
        Vector2D ret = new Vector2D(v);
        ret.normaliza();
        return ret;
    }

    /**
     * @param v Vector2D con el que haremos el producto escalar con this.
     * @return float con el valor del producto escalar
     */
    public float productoEscalar(Vector2D v) {
        return m_x * v.m_x + m_y * v.m_y;
    }

    /**
     * @param v1 primer Vector2D para hacer el producto escalar
     * @param v2 segundo Vector2D para hacer el producto escalar
     * @return float con el valor del producto escalar de ambos parametros
     */
    public static float productoEscalar(Vector2D v1, Vector2D v2) {
        return v1.m_x * v2.m_x + v1.m_y * v2.m_y;
    }

    /**
     * Método que calcula el módulo o longitud del vector this.
     *
     * @return Devuelve el módulo o longitud del vector
     */
    public float modulo() {
        return (float) Math.sqrt(m_x * m_x + m_y * m_y);
    }

    /**
     * Método estatic que calcula el módulo o longitud del vector pasado
     *
     * @return Devuelve el módulo o longitud del vector pasado
     */
    public static float modulo(Vector2D v) {
        return (float) Math.sqrt(v.m_x * v.m_x + v.m_y * v.m_y);
    }

    /**
     * Método que te dice si el vector this es nulo y por tanto no normalizable.
     *
     * @return devuleve un boolean con la respuesta
     */
    public boolean esNulo() {
        return m_x == 0f && m_y == 0f;
    }

    /**
     * Método que te dice si el vector pasado es nulo y por tanto no normalizable.
     *
     * @return devuleve un boolean con la respuesta
     */
    public boolean esVectorNulo(Vector2D v) {
        return v.m_x == 0f && v.m_y == 0f;
    }

    /**
     * @param v Vector2D con el que comparar
     * @return devuelve un boolean indicando si es perpendicular o no
     */
    public boolean esPerpendicularA(Vector2D v) {
        return productoEscalar(v) == 0;
    }

    /**
     * @param v1 primer Vector2D
     * @param v2 segundo Vector2D con el que mirar si es perpendicular
     * @return devuelve un boolean indicando si son perpendiculares entre ellos.
     */
    public static boolean sonPerpendiculares(Vector2D v1, Vector2D v2) {
        return productoEscalar(v1, v2) == 0;
    }

    /**
     * @param v Vector2D con el que mirar el angulo con this
     * @return devuelve los grados entre los vectores.
     */
    public float anguloEnGradosCon(Vector2D v) {
        return (float) (anguloEnRadianesCon(v) * 180f / Math.PI);
    }

    /**
     * @param v Vector2D con el que mirar el angulo con this
     * @return devuelve los radianes entre los vectores.
     */
    public float anguloEnRadianesCon(Vector2D v) {
        if (modulo() == 0 || modulo(v) == 0) {
            return 0;
        }
        return (float) Math.acos(productoEscalar(v) / modulo() / modulo(v));
    }


    /**
     * Función que calcula el angulo en grados [0..90] entre v1 y v2
     *
     * @param v1 primer Vector2D
     * @param v2 segundo Vector2D
     * @return devuelve un float con el número de grados que los separa.
     */
    public static float anguloEnGradosEntre(Vector2D v1, Vector2D v2) {
        return (float) (anguloEnRadianesEntre(v1, v2) * 180f / Math.PI);
    }

    /**
     * Función que calcula el angulo en radianes [0..PI/2] entre v1 y v2
     *
     * @param v1 primer Vector2D
     * @param v2 segundo Vector2D
     * @return devuelve un float con el número de grados que los separa.
     */
    public static float anguloEnRadianesEntre(Vector2D v1, Vector2D v2) {
        if (modulo(v1) == 0f || modulo(v2) == 0f) {
            return 0;
        }
        return (float) Math.acos(productoEscalar(v1, v2) / modulo(v1) / modulo(v2));
    }

    public void suma(Vector2D v) {
        m_x += v.m_x;
        m_y += v.m_y;
    }

    public void resta(Vector2D v) {
        m_x -= v.m_x;
        m_y -= v.m_y;
    }

    public void multipicaPorEscalar(float escalar) {
        m_x *= escalar;
        m_y *= escalar;
    }

    public void divisionPorEscalar(float escalar) {
        m_x /= escalar;
        m_y /= escalar;
    }

    public void invertir() {
        multipicaPorEscalar(-1f);
    }

    public static Vector2D suma(Vector2D v1, Vector2D v2) {
        Vector2D aux = new Vector2D(v1);
        aux.suma(v2);
        return aux;
    }

    public static Vector2D resta(Vector2D v1, Vector2D v2) {
        Vector2D aux = new Vector2D(v1);
        aux.resta(v2);
        return aux;
    }

    public static Vector2D multipicaPorEscalar(Vector2D v, float escalar) {
        Vector2D aux = new Vector2D(v);
        aux.multipicaPorEscalar(escalar);
        return aux;
    }

    public static Vector2D divisionPorEscalar(Vector2D v, float escalar) {
        Vector2D aux = new Vector2D(v);
        aux.divisionPorEscalar(escalar);
        return aux;
    }

    public static Vector2D invertidoDe(Vector2D v) {
        Vector2D aux = new Vector2D(v);
        aux.invertir();
        return aux;
    }

    public boolean igualA(Vector2D v) {
        return (m_x==v.m_x&&m_y==v.m_y);
    }

    public void igualarA(Vector2D v) {
        m_x = v.m_x;
        m_y = v.m_y;
    }
}
