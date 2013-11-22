package conduit.amqp.builder;

import conduit.amqp.AMQPConnectionProperties;
import conduit.amqp.AMQPPublishContext;
import conduit.amqp.AMQPPublishProperties;
import conduit.amqp.AMQPTransport;
import conduit.publisher.PublisherBuilder;

public class AMQPPublisherBuilder extends PublisherBuilder<AMQPTransport
                                                         , AMQPConnectionProperties
                                                         , AMQPPublishProperties
                                                         , AMQPPublishContext> {
    protected String username;
    protected String password;
    protected String exchange;
    protected String routingKey;
    protected String host = "localhost";
    protected String virtualHost = "/";
    protected int port = 5672;
    protected int publishTimeout = 100;
    protected int connectionTimeout = 0; //! 0 is infinite.
    protected int heartbeatInterval = 60; //! In seconds

    protected AMQPPublisherBuilder() {
    }

    public static AMQPPublisherBuilder builder() {
        return new AMQPPublisherBuilder();
    }

    public AMQPPublisherBuilder username(String username) {
        this.username = username;
        return this;
    }

    public AMQPPublisherBuilder password(String password) {
        this.password = password;
        return this;
    }

    public AMQPPublisherBuilder virtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
        return this;
    }

    public AMQPPublisherBuilder host(String host) {
        this.host = host;
        return this;
    }

    public AMQPPublisherBuilder port(int port) {
        this.port = port;
        return this;
    }

    public AMQPPublisherBuilder publishTimeout(int timeout) {
        this.publishTimeout = timeout;
        return this;
    }

    public AMQPPublisherBuilder connectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
        return this;
    }

    public AMQPPublisherBuilder heartbeatInterval(int heartbeatInterval) {
        this.heartbeatInterval = heartbeatInterval;
        return this;
    }

    public AMQPPublisherBuilder exchange(String exchange) {
        this.exchange = exchange;
        return this;
    }

    public AMQPPublisherBuilder routingKey(String routingKey) {
        this.routingKey = routingKey;
        return this;
    }

    @Override
    protected AMQPTransport buildTransport() {
        return new AMQPTransport(host, port);
    }

    @Override
    protected AMQPConnectionProperties buildConnectionProperties() {
        return new AMQPConnectionProperties(username, password, virtualHost, connectionTimeout, heartbeatInterval);
    }

    @Override
    protected AMQPPublishProperties buildPublishProperties() {
        return new AMQPPublishProperties(exchange, routingKey, publishTimeout);
    }

    @Override
    protected AMQPPublishContext buildPublishContext(AMQPTransport transport
                                                   , AMQPConnectionProperties connectionProperties
                                                   , AMQPPublishProperties publishProperties) {
        return new AMQPPublishContext(transport, connectionProperties, publishProperties);
    }

    @Override
    protected void validate() {
        assertNotNull(exchange, "exchange");
        assertNotNull(routingKey, "routingKey");
    }
}