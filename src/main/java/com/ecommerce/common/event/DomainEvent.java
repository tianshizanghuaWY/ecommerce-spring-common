package com.ecommerce.common.event;

import com.ecommerce.common.event.inventory.InventoryChangedEvent;
import com.ecommerce.common.event.order.OrderAddressChangedEvent;
import com.ecommerce.common.event.order.OrderCreatedEvent;
import com.ecommerce.common.event.order.OrderPaidEvent;
import com.ecommerce.common.event.order.OrderProductChangedEvent;
import com.ecommerce.common.event.product.ProductCreatedEvent;
import com.ecommerce.common.event.product.ProductNameUpdatedEvent;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.Instant;

import static com.ecommerce.common.utils.UuidGenerator.newUuid;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.EXISTING_PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import static java.time.Instant.now;

// Make sure to register the @JsonSubTypes when adding new concrete event classes
@JsonTypeInfo(use = NAME, include = EXISTING_PROPERTY, property = "_type", visible = true)
@JsonSubTypes({
        @Type(value = OrderCreatedEvent.class, name = "ORDER_CREATED"),
        @Type(value = OrderAddressChangedEvent.class, name = "ORDER_ADDRESS_CHANGED"),
        @Type(value = OrderPaidEvent.class, name = "ORDER_PAID"),
        @Type(value = OrderProductChangedEvent.class, name = "ORDER_PRODUCT_CHANGED"),
        @Type(value = ProductCreatedEvent.class, name = "PRODUCT_CREATED"),
        @Type(value = ProductNameUpdatedEvent.class, name = "PRODUCT_NAME_UPDATED"),
        @Type(value = InventoryChangedEvent.class, name = "INVENTORY_CHANGED")

})
public abstract class DomainEvent {
    private final String _id;
    private final DomainEventType _type;
    private final Instant _createdAt;

    @JsonCreator
    protected DomainEvent(@JsonProperty("_id") String _id,
                          @JsonProperty("_type") DomainEventType _type,
                          @JsonProperty("_createdAt") Instant _createdAt) {
        this._id = _id;
        this._type = _type;
        this._createdAt = _createdAt;
    }

    protected DomainEvent(DomainEventType _type) {
        this._id = newUuid();
        this._type = _type;
        this._createdAt = now();
    }

    public String get_id() {
        return _id;
    }

    public DomainEventType get_type() {
        return _type;
    }

    public Instant get_createdAt() {
        return _createdAt;
    }

    @Override
    public String toString() {
        return "DomainEvent{" + "_id='" + _id + '\'' + ", _type=" + _type + '}';
    }
}
