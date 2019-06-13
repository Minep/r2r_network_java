package com.lunaixsky.r2r;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.lunaixsky.common.TransportHeader;

public class R2R_Main {
	
	static InetAddress addr;
	static R2RPacket packet;
	static DatagramSocket socket;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			packet = new R2RPacket();
			socket = new DatagramSocket(8086);
			Thread t = new Thread(()-> {
				byte[] receive = new byte[1024];
				while(true)
				{
					DatagramPacket pkt = new DatagramPacket(receive, receive.length);
					try {
						socket.receive(pkt);
						System.out.println("\r\n Content received:");
						Utils.printByte(pkt.getData(),16);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
			t.start();
			addr = InetAddress.getByName("192.168.1.11");
			
			byte infoTag = 0x0;
			infoTag = packet.setTagComponent(infoTag, R2RPacket.PKTTYPE_INCOMING, R2RPacket.INFOTAG_PKTTYPE_MASK, R2RPacket.PKTTYPE_BITS);
			infoTag = packet.setTagComponent(infoTag, R2RPacket.PRORITY_GENERAL, R2RPacket.INFOTAG_PRORITY_MASK, R2RPacket.PRORITY_BITS);
			infoTag = packet.setTagComponent(infoTag, R2RPacket.CHLTYPE_GENERAL, R2RPacket.INFOTAG_CHLTYPE_MASK, R2RPacket.CHLTYPE_BITS);
			TransportHeader trh = packet.CreateTRheader(new byte[6], Utils.InetAddr2Int32(addr), 
					Utils.InetAddr2Int32(InetAddress.getLocalHost()), infoTag);
			
			byte[] content = Utils.TR2StructBin(trh);
			Utils.printByte(content, 16);
			DatagramPacket dpkt = new DatagramPacket(content, 0, content.length, addr, 8086);
			socket.send(dpkt);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
