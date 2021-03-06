package com.ibeiliao.support.web.log;
import org.apache.commons.io.input.TeeInputStream;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 功能：对Request的输入流进行再次包装
 * 详情：
 *
 * @author liaoming
 * @since 2017年07月13日
 */
public class RequestWrapper extends HttpServletRequestWrapper {

    private ByteArrayOutputStream bos = new ByteArrayOutputStream();

    public RequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            private TeeInputStream tee = new TeeInputStream(RequestWrapper.super.getInputStream(), bos, true);

            @Override
            public int read() throws IOException {
                return tee.read();
            }
        };
    }

    public byte[] toByteArray() {
        return bos.toByteArray();
    }
}
