package org.openremote.test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.openremote.agent.protocol.knx.KNXProtocol;

import tuwien.auto.calimero.FrameEvent;
import tuwien.auto.calimero.GroupAddress;
import tuwien.auto.calimero.IndividualAddress;
import tuwien.auto.calimero.KNXAddress;
import tuwien.auto.calimero.KNXFormatException;
import tuwien.auto.calimero.KNXTimeoutException;
import tuwien.auto.calimero.Priority;
import tuwien.auto.calimero.cemi.CEMI;
import tuwien.auto.calimero.cemi.CEMIFactory;
import tuwien.auto.calimero.cemi.CEMILData;
import tuwien.auto.calimero.internal.EventListeners;
import tuwien.auto.calimero.knxnetip.util.KnxAddressesDIB;
import tuwien.auto.calimero.link.AbstractLink;
import tuwien.auto.calimero.link.KNXLinkClosedException;
import tuwien.auto.calimero.link.NetworkLinkListener;
import tuwien.auto.calimero.link.medium.KNXMediumSettings;
import tuwien.auto.calimero.server.VirtualLink;

public class KNXTestingNetworkLink extends AbstractLink {

    private static final Logger LOG = Logger.getLogger(KNXTestingNetworkLink.class.getName());
    
    private static KNXTestingNetworkLink instance;
    private String lastDataReceived;
    
    public KNXTestingNetworkLink(Object... args) throws KNXFormatException {
       super("KNXTestingLink",  KNXMediumSettings.create(KNXMediumSettings.MEDIUM_TP1, new IndividualAddress("1.1.1")));
       KNXTestingNetworkLink.instance = this;
    }

    @Override
    protected void onSend(CEMILData msg, boolean waitForCon) throws KNXTimeoutException, KNXLinkClosedException {
        if (msg.getDestination().toString().equals("0/4/14")) {

        } else {
            LOG.info("KNX bus received message: " + msg);
            this.lastDataReceived = byteArrayToHex(msg.getPayload());
        }
    }
    
    @Override
    protected CEMI onReceive(FrameEvent e) throws KNXFormatException {
        return super.onReceive(e);
    }
    
    public static KNXTestingNetworkLink getInstance() {
        return KNXTestingNetworkLink.instance;
    }
    
    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for(byte b: a)
           sb.append(String.format("%02x", b));
        return sb.toString();
     }
    
    public String getLastDataReceived() {
        return this.lastDataReceived;
    }

    @Override
    protected void onSend(KNXAddress dst, byte[] msg, boolean waitForCon) throws KNXTimeoutException, KNXLinkClosedException {
        // TODO Auto-generated method stub
        
    }
}
