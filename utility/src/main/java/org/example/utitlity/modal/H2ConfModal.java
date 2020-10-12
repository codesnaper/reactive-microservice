package org.example.utitlity.modal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty({
        "h2.webport",
        "h2.tcpPort"
})
public class H2ConfModal {

    @Value("${h2.webport}")
    private String webport;

    @Value("${h2.tcpPort}")
    private String tcpPort;

    public String getWebport() {
        return webport;
    }

    public void setWebport(String webport) {
        this.webport = webport;
    }

    @Override
    public String toString() {
        return "H2ConfModal{" +
                "webport='" + webport + '\'' +
                ", tcpPort='" + tcpPort + '\'' +
                '}';
    }

    public String getTcpPort() {
        return tcpPort;
    }

    public void setTcpPort(String tcpPort) {
        this.tcpPort = tcpPort;
    }
}
