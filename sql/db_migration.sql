CREATE DATABASE fastfood_cms;
USE fastfood_cms;

ALTER TABLE menu_dish_detail
    ADD CONSTRAINT fk_menu_detail_to_materials
        FOREIGN KEY (material_id) REFERENCES materials(id);