package com.github.ram.kafka.tutorial1;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemoKeys {

    public static void main(String[] args) {

        String bootstrapServers = "127.0.0.1:9092";
        final Logger logger = LoggerFactory.getLogger(ProducerDemoKeys.class);
        //Create producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //Create Producer

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        // Create Producer Record
        for (int i=1; i<10; i++){
            final ProducerRecord<String,String> record = new ProducerRecord<String, String>("first_topic","Hey Java, Welcome!" + Integer.toString(i));

            //Send Data
            producer.send(record, new Callback() {
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if(e == null){
                        logger.info("recieved new metadata : \n" +
                                "Topic : " + recordMetadata.topic() + "\n"
                                + "Partition : " + recordMetadata.partition() + "\n"
                                + "Offset : " + recordMetadata.offset() + "\n"
                                + "Time Stamp :" + recordMetadata.timestamp());

                    }else{
                        logger.error("Error while producing " + e);

                    }
                }
            });

        }

        //flush data

        producer.flush();

        //close kafka
        producer.close();
    }
}
