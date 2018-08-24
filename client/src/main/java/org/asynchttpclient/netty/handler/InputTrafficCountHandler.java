package org.asynchttpclient.netty.handler;

import org.asynchttpclient.netty.NettyResponseFuture;
import org.asynchttpclient.netty.channel.Channels;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;

public class InputTrafficCountHandler extends ChannelInboundHandlerAdapter {
  
  protected final Logger logger = LoggerFactory.getLogger(getClass());
  
  @Override
  public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("http incoming traffic: {}", msg);
    }
    if (msg instanceof HttpContent) {
      int size = HttpContent.class.cast(msg).content().readableBytes();
      Channel channel = ctx.channel();
      Object attribute = Channels.getAttribute(channel);
      if (attribute != null && attribute instanceof NettyResponseFuture) {
        NettyResponseFuture.class.cast(attribute).getAndSetLastestRawBodyPartSize(size);
      }
    }
    ctx.fireChannelRead(msg);
  }

}
