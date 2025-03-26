ALTER TABLE product RENAME COLUMN condition TO itemCondition;
ALTER TABLE product RENAME COLUMN category TO itemCategory;
ALTER TABLE product_tags RENAME COLUMN tags TO itemTags;

ALTER TABLE product DROP CONSTRAINT product_tags_unique;
ALTER TABLE product ADD CONSTRAINT product_tags_unique UNIQUE (product_id, itemtags);

ALTER TABLE RENAME product_tags TO product_item_tags;