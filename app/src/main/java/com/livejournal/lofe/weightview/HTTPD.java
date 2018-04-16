package com.livejournal.lofe.weightview;

import android.app.Application;
import android.database.Cursor;
import android.util.Log;
import java.lang.String;

import fi.iki.elonen.NanoHTTPD;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.livejournal.lofe.weightview.MyUtil.log;

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
                        "google.charts.setOnLoadCallback(drawLine);" +
                        "function drawLine() {" +
                            "var data = new google.visualization.DataTable();" +
                            "data.addColumn('number', 'Date');" +
                            "data.addColumn('number', 'Масса');" +
                            "data.addRows([";
                            DB db = new DB(MyApplication.getContext());
                            db.openRead();
                            Cursor c = db.getAllWeight();
                            if (c.moveToFirst()) {
                                do {
                                    msg += "[" + c.getPosition() + ", " + c.getString(1) + "]";
                                    //"[0, 0],    [1, 5]" +
                                    if (c.getPosition() < c.getCount())
                                        msg += ", ";
                                } while (c.moveToNext());
                            }
                            c.close();
                            db.close();
                            msg +=
                            //msg += "[0, 0],    [1, 5]" +
                            "]);" +
                            "var options = {" +
                                "hAxis: {" +
                                    "title: 'Дата'" +
                                "}," +
                                "vAxis: {" +
                                    "title: 'Масса'" +
                                "}," +
                                "colors: ['#a52714']" +
                            "};" +
                            "var chart = new google.visualization.LineChart(document.getElementById('chart_div'));" +
                            "chart.draw(data, options);" +
                        "}" +
                    "</script>" +
                "</head>" +
                "<body>" +
                    "<h1>График веса</h1>\n" +
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
