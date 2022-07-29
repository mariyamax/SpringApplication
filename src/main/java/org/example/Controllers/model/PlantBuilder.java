package org.example.Controllers.model;



public class PlantBuilder {
    private String name;
    private String type;
    private String area;
    private String author;
    private Boolean isSale;
    private Integer coast;
    private String description;
    private User user;


    public PlantBuilder PlantName(String name){
        this.name=name;
        return this;
    }
    public PlantBuilder isSale(Boolean isSale){
        this.isSale=isSale;
        return this;
    }
    public PlantBuilder coast(Integer coast){
        this.coast=coast;
        return this;
    }


    public PlantBuilder PlantUser(User user){
        this.user=user;
        return this;
    }
    public PlantBuilder PlantType(String type){
        this.type=type;
        return this;
    }
    public PlantBuilder area(String area){
        this.area=area;
        return this;
    }
    public PlantBuilder author(String author){
        this.author=author;
        return this;
    }
    public PlantBuilder description(String description){
        this.description=description;
        return this;
    }

    public Plant build(){
        Plant plant = new Plant();
        plant.setArea(area);
        plant.setAuthor(author);
        plant.setCoast(coast);
        plant.setDescription(description);
        plant.setIsSale(isSale);
        plant.setName(name);
        plant.setType(type);
        plant.setUser(user);
        return plant;
    }
}
