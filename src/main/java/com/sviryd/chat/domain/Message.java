package com.sviryd.chat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.sviryd.chat.converter.LocalDateTimeToTimestampConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@ToString(of = {"id", "creationLDT", "message"})
@EqualsAndHashCode(of = {"creationLDT", "message"}, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(value = {}, ignoreUnknown = true)
@JsonPropertyOrder({"id", "creationLDT", "authorId", "message"})
@Entity
@Table
public class Message implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @UuidGenerator
    @JsonView(Views.Id.class)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    @JsonView(Views.Author.class)
    private User author;

    @JsonView(Views.AuthorId.class)
    @Column(name = "author_id", updatable = false, insertable = false)
    private Long authorId;


    @CreationTimestamp
    @Convert(converter = LocalDateTimeToTimestampConverter.class)
    @JsonView(Views.CreationLDT.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonProperty("time")
    private LocalDateTime creationLDT = LocalDateTime.now();

    @Column(length = 255)
    @JsonView(Views.Message.class)
    private String message;
}
