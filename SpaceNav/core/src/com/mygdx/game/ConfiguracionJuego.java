package com.mygdx.game;
import java.io.*;
import java.util.Properties;

public class ConfiguracionJuego {
    private static ConfiguracionJuego instancia = null;
    private int volumen;
    private static final String ARCHIVO_CONFIGURACION = "configuracion.properties";

    private ConfiguracionJuego() {
        cargarConfiguracion();
    }

    public static ConfiguracionJuego getInstance() {
        if (instancia == null) {
            instancia = new ConfiguracionJuego();
        }
        return instancia;
    }

    private void cargarConfiguracion() {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(ARCHIVO_CONFIGURACION));
            volumen = Integer.parseInt(prop.getProperty("volumen", "50")); // 50 es el valor predeterminado
        } catch (IOException e) {
            e.printStackTrace();
            volumen = 50; // Valor predeterminado en caso de error
        }
    }

    public void guardarConfiguracion() {
        Properties prop = new Properties();
        prop.setProperty("volumen", String.valueOf(volumen));
        try {
            prop.store(new FileOutputStream(ARCHIVO_CONFIGURACION), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Métodos de acceso y modificación del volumen
    public int getVolumen() {
        return volumen;
    }

    public void setVolumen(int nuevoVolumen) {
        this.volumen = nuevoVolumen;
        guardarConfiguracion(); // Guardar cada vez que se cambia el volumen
    }
}
