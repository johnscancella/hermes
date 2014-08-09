package com.scancella.hermes.network.services;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import com.scancella.hermes.domain.LoggingObject;
import com.scancella.hermes.network.domain.FileSendingResponse;
import com.scancella.hermes.network.domain.Server;

public class DynamicSpringIntegrationFileSender extends LoggingObject implements FileSender
{
  @Autowired
  private NetworkService router;

  @Override
  public FileSendingResponse sendFiles(Collection<File> files, String destinationServer)
  {
    // TODO Auto-generated method stub
//    * psedocode
//    * =========================
//    * 
//    * find shortest route for files to server
    List<Server> shortestPath = router.getShortestRoute(destinationServer);
//    * compress file before sending
//    * get server meta-data, and use it to create ftp session
//    * create message with compressed files
    MessageBuilder mb = MessageBuilder.withPayload(files);
    Message message = mb.build();
//    * send message to next server in the chain 
    return null;
  }

}
