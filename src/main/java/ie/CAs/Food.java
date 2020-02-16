package ie.CAs;

public class Food {
    private String name;
    private String description;
    private float popularity;
    private Integer price;
    private String logo;

    public Food(String name, String description, float popularity, Integer price, String imageUrl) {
        this.name = name;
        this.description = description;
        this.popularity = popularity;
        this.price = price;
        this.logo = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImageUrl() {
        return logo;
    }

    public void setImageUrl(String imageUrl) {
        this.logo = imageUrl;
    }
}
