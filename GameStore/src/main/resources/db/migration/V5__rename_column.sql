ALTER TABLE product RENAME COLUMN condition TO itemCondition;
ALTER TABLE product RENAME COLUMN category TO itemCategory;
ALTER TABLE product_tags RENAME COLUMN tags TO itemTags;

ALTER TABLE product ADD CONSTRAINT product_tags_unique UNIQUE (id);

--ALTER TABLE product_tags RENAME TO product_item_tags;