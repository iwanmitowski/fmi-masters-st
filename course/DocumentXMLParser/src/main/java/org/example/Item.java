package org.example;

@JsonEntity
public class Item {

    @JsonField(title = "bookk")
    @Documentable
    private String name = "Book";

    @JsonField(title = "bookTitlee")
    @Documentable(title = "sub_title")
    private String subTitle = "Science";

    @JsonField(
        title = "priceee",
        expectedType = JsonType.PLAIN)
    @Documentable
    private double price = 12.0;
}
