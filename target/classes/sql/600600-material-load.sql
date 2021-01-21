INSERT INTO material (id, materiallevel_id, kind, name, code) VALUES (1, 2, 1, 'Кока-Кола в бутылках 0.5', NULL);
INSERT INTO material (id, materiallevel_id, kind, name, code) VALUES (2, 2, 1, 'Бананы', NULL);
INSERT INTO material (id, materiallevel_id, kind, name, code) VALUES (3, 3, 2, 'Уборка помещения', NULL);
INSERT INTO material (id, materiallevel_id, kind, name, code) VALUES (4, 3, 2, 'Доставка товара до квартиры', NULL);
ALTER SEQUENCE material_id_gen RESTART WITH 5;
