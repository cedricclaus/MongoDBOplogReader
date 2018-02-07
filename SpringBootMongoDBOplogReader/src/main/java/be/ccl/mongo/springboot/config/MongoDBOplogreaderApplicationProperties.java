package be.ccl.mongo.springboot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oplog")
public class MongoDBOplogreaderApplicationProperties {


    String name;

    private long sleepTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(long sleepTime) {
        this.sleepTime = sleepTime;
    }


    private final Rabbit rabbit = new Rabbit();

    public Rabbit getRabbit() {
        return rabbit;
    }

    public static class Rabbit {

        private boolean enabled;

        private String topic;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }
    }
}
