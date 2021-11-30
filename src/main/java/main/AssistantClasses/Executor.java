package main.AssistantClasses;

import main.Main;

import java.util.ArrayList;


public class Executor {

     private static int freeDeviceNumber = 0;

     private static int selectedSource = -1;

     public void toDevice(ArrayList<Buffer> buffers, ArrayList<Device> devices,
                          ArrayList<Source.Request> requestList, ArrayList<Source> sources) {
          boolean isEmptyAnyone = false;

          for (int i = 0; i < devices.size(); i++) {
               if (devices.get(i).isEmpty()) {
                    isEmptyAnyone = true;
                    freeDeviceNumber = devices.get(i).getNumber();
                    break;
               }
          }

          if (isEmptyAnyone) {
               boolean isBuffersEmpty = true;
               for (Buffer buffer : buffers) {
                    if (!buffer.isEmpty())
                         isBuffersEmpty = false;
               }

               if (!isBuffersEmpty) {
                    Source.Request req = null;
                    int numberSource = Main.countSources + 1;
                    int numberRequest = Main.countRequests;

                    for (Buffer buffer : buffers) {
                         if (buffer.getNumberSource() == selectedSource){
                              numberSource=buffer.getNumberSource();
                              break;
                         }
                         if (buffer.getNumberSource() < numberSource && buffer.getNumberSource() != 0)
                              numberSource = buffer.getNumberSource();
                    }
                    selectedSource = numberSource;

                    int numberBuffer = 0;
                    for (int i = 0; i < buffers.size(); i++) {
                         if (buffers.get(i).getNumberSource() == numberSource && buffers.get(i).getRequest().getNumber() < numberRequest
                                 && buffers.get(i).getRequest().getNumber() != 0) {
                              numberRequest = buffers.get(i).getRequest().getNumber();
                              req = buffers.get(i).getRequest();
                              numberBuffer = i;
                         }
                    }

                    buffers.get(numberBuffer).delete();

                    sources.get(numberSource - 1).setTimeBufferForService(buffers.get(numberBuffer).gettForSource());//информация о нахождении заявки из нужного источника

                    Main.systemTime = buffers.get(numberBuffer).getTimeOut();


                    if (req != null) {
                         devices.get(freeDeviceNumber - 1).add(req, numberSource);
                         Main.systemTime = devices.get(freeDeviceNumber - 1).getTimeAdd();
                    }

//                    buffers.get(numberBuffer).clear();


//                    for (int i = numberBuffer; i < buffers.size() - 1; i++) {
//                         buffers.set(i, buffers.get(i + 1));
//                    }
//
//                    buffers.get(Main.countBuffers - 1).clear();
//                    buffers.get(Main.countBuffers - 1).setNumber(Main.countBuffers);



                    for (int i = numberBuffer; i < buffers.size() - 1; i++) {
                         buffers.set(i, new Buffer(buffers.get(i + 1)));
                         buffers.get(i).setNumber(i+1);
                    }

                    buffers.get(Main.countBuffers - 1).clear();
//                    buffers.get(Main.countBuffers - 1).setNumber(Main.countBuffers);
               }
          }


          Device device = devices.get(0);
          for(Device d : devices){
               if(!d.isEmpty()) {
                    device = d;
                    break;
               }
          }

          double minTr = device.getTimeToTreatment();
          int numberDevice = device.getNumber();
          for (Device d : devices) {
               if (d.getTimeToTreatment() < minTr && !d.isEmpty()) {
                    minTr = d.getTimeToTreatment();
                    numberDevice = d.getNumber();
               }
          }

          double minGr = requestList.get(0).gettGiner();
          for (Source.Request req : requestList) {
               if (req.gettGiner() < minGr)
                    minGr = req.gettGiner();
          }

          if (minTr < minGr)
          {
               Main.systemTime = minTr;
               int numberSource = device.getNumberSource();
               devices.get(numberDevice - 1).delete();
               sources.get(numberSource - 1).setTimeServiceForSource(devices.get(numberDevice - 1).gettForSource());
          }
     }
}
