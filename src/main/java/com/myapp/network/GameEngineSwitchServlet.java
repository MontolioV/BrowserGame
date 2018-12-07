package com.myapp.network;

import com.myapp.core.Engine;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>Created by MontolioV on 28.11.18.
 */
@WebServlet(value = "engine-switch")
public class GameEngineSwitchServlet extends HttpServlet {
    @Inject
    private Engine engine;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (engine.isEngineRunning()) {
            engine.stopEngine();
        } else {
            engine.startEngine();
        }
    }

    //Getter & Setters

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
