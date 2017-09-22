package org.eclipse.jetty.embedded;

import org.eclipse.jetty.server.HttpOutput;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;

class FastFileHandler extends AbstractHandler
{
    private final File file;
    private String domain;
    private int port;

    public FastFileHandler(File file, String domain, int port)
    {
        this.file = file;
        this.domain = domain;
        this.port = port;
    }

    @Override
    public void handle(
        String target,
        Request baseRequest,
        HttpServletRequest request,
        HttpServletResponse response )
            throws IOException, ServletException
    {
        baseRequest.setHandled(true);
        response.setContentType("text/html");
        response.setHeader("Content-Security-Policy", "default-src 'self'");
        response.setHeader("Content-Security-Policy", "connect-src ws://" + domain + ":" + port);
        // need to caste to Jetty output stream for best API
        ((HttpOutput) response.getOutputStream())
                .sendContent(FileChannel.open(file.toPath(),
                        StandardOpenOption.READ));
        return;
    }
}