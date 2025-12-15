package network.shop;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import network.game.PlayerStorage;
import network.netty.MapleNettyDecoder;
import network.netty.MapleNettyEncoder;
import network.netty.MapleNettyHandler;
import network.netty.ServerType;
import objects.utils.ServerProperties;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.buffer.SimpleBufferAllocator;

public class CashShopServer {
   private static String ip;
   private static final int PORT = 8700;
   private static PlayerStorage players;
   private static boolean finishedShutdown = false;
   private static ServerBootstrap bootstrap;

   public static final void startCashShopServer() {
      ip = ServerProperties.getProperty("net.sf.odinms.world.host") + ":8700";
      players = new PlayerStorage(-10);
      IoBuffer.setUseDirectBuffer(false);
      IoBuffer.setAllocator(new SimpleBufferAllocator());
      EventLoopGroup bossGroup = new NioEventLoopGroup(24);
      EventLoopGroup workerGroup = new NioEventLoopGroup(24);

      try {
         bootstrap = new ServerBootstrap();
         ((ServerBootstrap) ((ServerBootstrap) bootstrap.group(bossGroup, workerGroup)
               .channel(NioServerSocketChannel.class))
               .childHandler(new ChannelInitializer<SocketChannel>() {
                  public void initChannel(SocketChannel ch) throws Exception {
                     ch.pipeline().addLast("decoder", new MapleNettyDecoder());
                     ch.pipeline().addLast("encoder", new MapleNettyEncoder());
                     ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(60, 30, 0));
                     ch.pipeline().addLast("handler", new MapleNettyHandler(ServerType.SHOP, -1));
                  }
               })
               .option(ChannelOption.SO_BACKLOG, 128))
               .childOption(ChannelOption.SO_SNDBUF, 4194304)
               .childOption(ChannelOption.SO_KEEPALIVE, true);
         ChannelFuture f = bootstrap.bind(8700).sync();
         System.out.println("Cash Shop Server successfully opened port 8700.");
      } catch (InterruptedException var3) {
         System.err.println("[Error] Cash Shop Server failed to open port 8700.");
         var3.printStackTrace();
      }
   }

   public static final String getIP() {
      return ip;
   }

   public static final PlayerStorage getPlayerStorage() {
      return players;
   }

   public static final void shutdown() {
      if (!finishedShutdown) {
         System.out.println("Saving all users connected to Cash Shop Server.");
         players.disconnectAll();
         System.out.println("Cash Shop Server is shutting down.");
         finishedShutdown = true;
      }
   }

   public static boolean isShutdown() {
      return finishedShutdown;
   }
}
