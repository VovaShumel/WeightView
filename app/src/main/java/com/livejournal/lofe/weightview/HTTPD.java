package com.livejournal.lofe.weightview;

import android.util.Log;
import java.lang.String;

import fi.iki.elonen.NanoHTTPD;

import java.io.IOException;
import java.util.List;
import java.util.Map;

class HTTPD {

    private WebServer server;

    HTTPD() {
        server = new WebServer();
        try {
            server.start();
        } catch(IOException ioe) {
            Log.w("Httpd", "The server could not start.");
        }
        Log.w("Httpd", "Web server initialized.");
    }

    void destroy() {
        if (server != null)
            server.stop();
    }

    private class WebServer extends NanoHTTPD {

        WebServer() {
            super(8080);
        }

        @Override
        public Response serve(IHTTPSession session) {
            String msg =
            "<html>" +
                "<head>" +
                    "<script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>" +
                    "<script type=\"text/javascript\">" +
                        "google.charts.load('current', {packages: ['corechart', 'line']});" +
                        "google.charts.setOnLoadCallback(drawLineColors);" +
                        "function drawLineColors() {" +
                            "var data = new google.visualization.DataTable();" +
                            "data.addColumn('number', 'X');" +
                            "data.addColumn('number', 'Dogs');" +
                            "data.addColumn('number', 'Cats');" +
                            "data.addRows([" +
                                "[0, 0, 0],    [1, 10, 5]" +
                            "]);" +
                            "var options = {" +
                                "hAxis: {" +
                                    "title: 'Time'" +
                                "}," +
                                "vAxis: {" +
                                    "title: 'Popularity'" +
                                "}," +
                                "colors: ['#a52714', '#097138']" +
                            "};" +
                            "var chart = new google.visualization.LineChart(document.getElementById('chart_div'));" +
                            "chart.draw(data, options);" +
                        "}" +
                    "</script>" +
                "</head>" +
                "<body>" +
                    "<h1>Weight chart</h1>\n" +
                    "<div id=\"chart_div\" style=\"width: 900px; height: 500px\"></div>" +
                "/<body>" +
            "/<html>\n";
            //Map<String, String> parms = session.getParms();
            Map<String, List<String>> parms = session.getParameters();
            if (parms.get("username") == null) {

            } else {
                msg += "<p>Hello, " + parms.get("username") + "!</p>";
            }
            //return newFixedLengthResponse( msg + "</body></html>\n" );
            return newFixedLengthResponse(msg);
        }
    }
}
