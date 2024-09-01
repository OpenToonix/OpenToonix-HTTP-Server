CREATE TABLE IF NOT EXISTS Users (
    User_id INT UNSIGNED PRIMARY KEY,
    Username VARCHAR(12) NOT NULL UNIQUE,
    Email VARCHAR(50) NOT NULL UNIQUE,
    Age TINYINT(2) UNSIGNED NOT NULL,
    Birthdate DATE NOT NULL,
    Gender ENUM('FEMALE', 'MALE') NOT NULL,
    Password VARCHAR(60) NOT NULL,
    Registered_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Roles (
    Role_id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(30) NOT NULL UNIQUE,
    Description VARCHAR(100) NOT NULL UNIQUE,
    Added_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS User_roles (
    User_id INT UNSIGNED NOT NULL,
    Role_id INT UNSIGNED NOT NULL,
    Assigned_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (User_id) REFERENCES Users(User_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (Role_id) REFERENCES Roles(Role_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Permissions (
    Permission_id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(30) NOT NULL UNIQUE,
    Description VARCHAR(100) NOT NULL UNIQUE,
    Added_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Role_permissions (
    Role_id INT UNSIGNED NOT NULL,
    Permission_id INT UNSIGNED NOT NULL,
    Assigned_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (Role_id) REFERENCES Roles(Role_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (Permission_id) REFERENCES Permissions(Permission_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS User_permissions (
    User_id INT UNSIGNED NOT NULL,
    Permission_id INT UNSIGNED NOT NULL,
    Assigned_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (User_id) REFERENCES Users(User_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (Permission_id) REFERENCES Permissions(Permission_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Avatar_body_part_categories (
    Avatar_body_part_category_id INT UNSIGNED PRIMARY KEY,
    Name VARCHAR(45) NOT NULL UNIQUE,
    Added_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Avatar_body_parts (
    Avatar_body_part_id INT UNSIGNED PRIMARY KEY,
    Title VARCHAR(45) NOT NULL,
    Category_id INT UNSIGNED,
    Type ENUM('BODY', 'EYE', 'HEAD', 'MOUTH') NOT NULL,
    Price INT UNSIGNED NOT NULL,
    Path VARCHAR(100) NOT NULL,
    Added_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (Category_id) REFERENCES Avatar_body_part_categories(Avatar_body_part_category_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Avatar_costumes (
    Avatar_costume_id INT UNSIGNED PRIMARY KEY,
    Title VARCHAR(45) NOT NULL UNIQUE,
    Category_id INT UNSIGNED,
    Price INT UNSIGNED NOT NULL,
    Head_path VARCHAR(100) NOT NULL,
    Body_path VARCHAR(100) NOT NULL,
    Added_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (Category_id) REFERENCES Avatar_body_part_categories(Avatar_body_part_category_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Avatars (
    Avatar_id INT UNSIGNED PRIMARY KEY,
    Costume_id INT UNSIGNED NOT NULL,
    Skin_color INT UNSIGNED NOT NULL,
    Head_id INT UNSIGNED NOT NULL,
    Head_color INT UNSIGNED NOT NULL,
    Eyes_id INT UNSIGNED NOT NULL,
    Mouth_id INT UNSIGNED NOT NULL,
    Body_id INT UNSIGNED NOT NULL,
    Body_color INT UNSIGNED NOT NULL,
    Image_path VARCHAR(100) NOT NULL,
    Credits INT UNSIGNED NOT NULL DEFAULT 150,
    User_id INT UNSIGNED NOT NULL,
    Created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (Costume_id) REFERENCES Avatar_costumes(Avatar_costume_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (Head_id) REFERENCES Avatar_body_parts(Avatar_body_part_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (Eyes_id) REFERENCES Avatar_body_parts(Avatar_body_part_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (Mouth_id) REFERENCES Avatar_body_parts(Avatar_body_part_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (Body_id) REFERENCES Avatar_body_parts(Avatar_body_part_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (User_id) REFERENCES Users(User_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Cube_areas (
    Cube_area_id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(45) NOT NULL,
    Added_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Cube_items (
    Cube_item_id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    Type_id VARCHAR(45) NOT NULL UNIQUE,
    Type ENUM('covering', 'mobile', 'structure') NOT NULL,
    Price INT UNSIGNED NOT NULL,
    Extra_properties JSON NOT NULL,
    Children_elements JSON NOT NULL,
    Is_default_owned TINYINT(1) NOT NULL DEFAULT 0,
    Added_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Cube_item_categories (
    Cube_item_category_id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    Xml_id VARCHAR(20) NOT NULL UNIQUE,
    Names JSON NOT NULL,
    Icon VARCHAR(35) NOT NULL UNIQUE,
    Added_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Cube_item_areas (
    Cube_item_area_id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    Cube_item_id INT UNSIGNED NOT NULL,
    Cube_area_id INT UNSIGNED NOT NULL,
    Added_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (Cube_item_id)
    REFERENCES Cube_items (Cube_item_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (Cube_area_id)
    REFERENCES Cube_areas (Cube_area_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Cube_item_category_assigns (
    Cube_item_category_assign_id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    Cube_item_id INT UNSIGNED NOT NULL,
    Cube_item_category_id INT UNSIGNED NOT NULL,
    Assigned_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (Cube_item_id)
    REFERENCES Cube_items (Cube_item_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (Cube_item_category_id)
    REFERENCES Cube_item_categories (Cube_item_category_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Username_blacklist (
    Username_id INT UNSIGNED PRIMARY KEY NOT NULL,
    Username VARCHAR(12) NOT NULL UNIQUE,
    Added_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE OR REPLACE VIEW V_User_information AS
SELECT
    u.User_id,
    u.Username,
    u.Gender,
    u.Age,
    a.Costume_id,
    a.Skin_color,
    a.Head_id,
    a.Head_color,
    a.Eyes_id,
    a.Mouth_id,
    a.Body_id,
    a.Body_color,
    a.Credits,
    a.Image_path
FROM Avatars a
INNER JOIN Users u USING (User_id);

INSERT INTO Avatar_body_part_categories (Avatar_body_part_category_id, Name)
VALUES
    (1, 'Ben 10'),
    (2, 'Ben 10: Omniverse'),
    (3, 'Ben 10: supremacía alienígena'),
    (4, 'Cartoon Network'),
    (5, 'El Chavo'),
    (6, 'Generador Rex'),
    (7, 'Gumball'),
    (8, 'Hora de aventura'),
    (9, 'Looney Tunes'),
    (10, 'Scooby-Doo'),
    (11, 'Tom y Jerry'),
    (12, 'Un Show Más');

INSERT INTO Cube_areas (Cube_area_id, Name)
VALUES
    (1, 'ceiling'),
    (2, 'floor'),
    (3, 'wall_back'),
    (4, 'wall_left'),
    (5, 'wall_right');

INSERT INTO Cube_item_categories (Cube_item_category_id, Xml_id, Names, Icon)
VALUES
    (1, 'accesorios', '{"en": "Accesories", "es": "Accesorios", "pt": "Acessórios"}', 'CategoryIcon_accesorios'),
    (2, 'floor', '{"en": "Floor", "es": "Pisos", "pt": "Chão"}', 'CategoryIcon_abajo'),
    (3, 'luces', '{"en": "Lights", "es": "Luces", "pt": "Iluminação"}', 'CategoryIcon_luces'),
    (4, 'mascota', '{"en": "Pets", "es": "Mascotas", "pt": "Mascotes"}', 'CategoryIcon_mascota'),
    (5, 'mesas', '{"en": "Tables", "es": "Mesas", "pt": "Mesas"}', 'CategoryIcon_mesas'),
    (6, 'plantas', '{"en": "Plants", "es": "Plantas"}', 'CategoryIcon_planta'),
    (7, 'sillas', '{"en": "Chairs", "es": "Sillas", "pt": "Cadeiras"}', 'CategoryIcon_sillas'),
    (8, 'structure', '{"en": "Structure", "es": "Estructura", "pt": "Estrutura"}', 'CategoryIcon_estructura'),
    (9, 'tvaudio', '{"en": "TV/Audio", "es": "TV/Audio", "pt": "TV/Áudio"}', 'CategoryIcon_tv'),
    (10, 'wall_back', '{"en": "Wall Back", "es": "Pared Fondo", "pt": "Fundo"}', 'CategoryIcon_atras'),
    (11, 'wall_left', '{"en": "Wall Left", "es": "Pared Izq.", "pt": "Parede Esq."}', 'CategoryIcon_izquierda'),
    (12, 'wall_right', '{"en": "Wall Right", "es": "Pared Der.", "pt": "Parede Dir."}', 'CategoryIcon_derecha');

LOAD DATA LOCAL INFILE '${csv_folder_path}/Avatar_body_parts.csv' INTO TABLE Avatar_body_parts
    FIELDS TERMINATED BY ';'
    OPTIONALLY ENCLOSED BY '\\'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES
    (Avatar_body_part_id, Title, Category_id, Type, Price, Path);

LOAD DATA LOCAL INFILE '${csv_folder_path}/Avatar_costumes.csv' INTO TABLE Avatar_costumes
    FIELDS TERMINATED BY ';'
    OPTIONALLY ENCLOSED BY '\\'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES
    (Avatar_costume_id, Title, Category_id, Price, Head_path, Body_path);

LOAD DATA LOCAL INFILE '${csv_folder_path}/Cube_items.csv' INTO TABLE Cube_items
    FIELDS TERMINATED BY ';'
    OPTIONALLY ENCLOSED BY '\\'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES
    (Cube_item_id, Type_id, Type, Price, Extra_properties, Children_elements, Is_default_owned);

INSERT INTO Cube_item_category_assigns (Cube_item_id, Cube_item_category_id)
VALUES
    (1, 8),
    (2, 2),
    (3, 3),
    (3, 5),
    (4, 7),
    (5, 7),
    (6, 7),
    (7, 7),
    (8, 11),
    (9, 12),
    (10, 10),
    (11, 3),
    (12, 1),
    (13, 3),
    (14, 1),
    (15, 1),
    (16, 1),
    (17, 7),
    (18, 7),
    (19, 7),
    (20, 1),
    (21, 1),
    (22, 1),
    (23, 4),
    (24, 1),
    (25, 1),
    (26, 1),
    (27, 1),
    (28, 3),
    (29, 10),
    (30, 2),
    (31, 11),
    (32, 12),
    (33, 10),
    (34, 2),
    (35, 11),
    (36, 12),
    (37, 10),
    (38, 2),
    (39, 11),
    (40, 12),
    (41, 10),
    (42, 2),
    (43, 11),
    (44, 12),
    (45, 10),
    (46, 2),
    (47, 11),
    (48, 12),
    (49, 10),
    (50, 2),
    (51, 11),
    (52, 12),
    (53, 10),
    (54, 11),
    (55, 12),
    (56, 10),
    (57, 11),
    (58, 12),
    (59, 2),
    (60, 10),
    (61, 2),
    (62, 11),
    (63, 12),
    (64, 10),
    (65, 11),
    (66, 12),
    (67, 1),
    (68, 1),
    (69, 1),
    (70, 1),
    (71, 1),
    (72, 3),
    (73, 1),
    (74, 1),
    (75, 1),
    (76, 1),
    (77, 1),
    (78, 1),
    (79, 1),
    (80, 1),
    (81, 1),
    (82, 1),
    (83, 1),
    (84, 1),
    (85, 1),
    (86, 1),
    (87, 1),
    (88, 1),
    (89, 1),
    (90, 1),
    (91, 1),
    (92, 1),
    (93, 1),
    (94, 1),
    (95, 1),
    (96, 1),
    (97, 1),
    (98, 1),
    (99, 1),
    (100, 1),
    (101, 3),
    (102, 1),
    (103, 1),
    (104, 4),
    (105, 4),
    (106, 1),
    (107, 1),
    (108, 1),
    (109, 1),
    (110, 3),
    (111, 7),
    (112, 9),
    (113, 5),
    (114, 5),
    (115, 5),
    (116, 5),
    (117, 1),
    (118, 1),
    (119, 1),
    (120, 4),
    (121, 9),
    (122, 1),
    (123, 1),
    (124, 3),
    (125, 1),
    (126, 9),
    (127, 1),
    (128, 1),
    (129, 1),
    (130, 7),
    (131, 1),
    (132, 3),
    (133, 1),
    (134, 3),
    (135, 3),
    (136, 6),
    (137, 6),
    (138, 1),
    (139, 6),
    (140, 6),
    (141, 6),
    (142, 6),
    (143, 1),
    (144, 7),
    (145, 7),
    (146, 7),
    (147, 1),
    (148, 3),
    (149, 3),
    (150, 3),
    (151, 3),
    (152, 3),
    (153, 5),
    (154, 5),
    (155, 1),
    (156, 1),
    (157, 9),
    (158, 9),
    (159, 9),
    (160, 9),
    (161, 9),
    (162, 9),
    (163, 9),
    (164, 1),
    (165, 1),
    (166, 1),
    (167, 4),
    (168, 4),
    (169, 4),
    (170, 4);

INSERT INTO Cube_item_areas (Cube_item_id, Cube_area_id)
VALUES
    (2, 2),
    (3, 2),
    (4, 2),
    (5, 2),
    (6, 2),
    (7, 2),
    (8, 4),
    (9, 5),
    (10, 3),
    (11, 3),
    (11, 4),
    (11, 5),
    (12, 2),
    (13, 2),
    (14, 2),
    (15, 2),
    (16, 2),
    (17, 2),
    (18, 2),
    (19, 2),
    (20, 2),
    (21, 2),
    (22, 2),
    (23, 2),
    (24, 2),
    (25, 2),
    (26, 3),
    (26, 4),
    (26, 5),
    (27, 2),
    (28, 2),
    (29, 3),
    (30, 2),
    (31, 4),
    (32, 5),
    (33, 3),
    (34, 2),
    (35, 4),
    (36, 5),
    (37, 3),
    (38, 2),
    (39, 4),
    (40, 5),
    (41, 3),
    (42, 2),
    (43, 4),
    (44, 5),
    (45, 3),
    (46, 2),
    (47, 4),
    (48, 5),
    (49, 3),
    (50, 2),
    (51, 4),
    (52, 5),
    (53, 3),
    (54, 4),
    (55, 5),
    (56, 3),
    (57, 4),
    (58, 5),
    (59, 2),
    (60, 3),
    (61, 2),
    (62, 4),
    (63, 5),
    (64, 3),
    (65, 4),
    (66, 5),
    (67, 2),
    (68, 2),
    (69, 2),
    (70, 2),
    (71, 2),
    (72, 3),
    (72, 4),
    (72, 5),
    (73, 2),
    (74, 2),
    (75, 2),
    (76, 2),
    (77, 3),
    (77, 4),
    (77, 5),
    (78, 3),
    (78, 4),
    (78, 5),
    (79, 3),
    (79, 4),
    (79, 5),
    (80, 3),
    (80, 4),
    (80, 5),
    (81, 3),
    (81, 4),
    (81, 5),
    (82, 3),
    (82, 4),
    (82, 5),
    (83, 3),
    (83, 4),
    (83, 5),
    (84, 3),
    (84, 4),
    (84, 5),
    (85, 3),
    (85, 4),
    (85, 5),
    (86, 3),
    (86, 4),
    (86, 5),
    (87, 3),
    (87, 4),
    (87, 5),
    (88, 3),
    (88, 4),
    (88, 5),
    (89, 3),
    (89, 4),
    (89, 5),
    (90, 3),
    (90, 4),
    (90, 5),
    (91, 3),
    (91, 4),
    (91, 5),
    (92, 3),
    (92, 4),
    (92, 5),
    (93, 3),
    (93, 4),
    (93, 5),
    (94, 3),
    (94, 4),
    (94, 5),
    (95, 3),
    (95, 4),
    (95, 5),
    (96, 3),
    (96, 4),
    (96, 5),
    (97, 3),
    (97, 4),
    (97, 5),
    (98, 3),
    (98, 4),
    (98, 5),
    (99, 3),
    (99, 4),
    (99, 5),
    (100, 2),
    (101, 1),
    (102, 2),
    (103, 2),
    (104, 2),
    (105, 2),
    (106, 3),
    (106, 4),
    (106, 5),
    (107, 3),
    (108, 3),
    (109, 2),
    (110, 1),
    (111, 2),
    (112, 2),
    (113, 2),
    (114, 2),
    (115, 2),
    (116, 2),
    (117, 3),
    (117, 4),
    (117, 5),
    (118, 2),
    (119, 2),
    (120, 2),
    (121, 2),
    (122, 2),
    (123, 2),
    (124, 1),
    (125, 2),
    (126, 2),
    (127, 3),
    (127, 4),
    (127, 5),
    (128, 3),
    (128, 4),
    (128, 5),
    (129, 2),
    (130, 2),
    (131, 1),
    (132, 1),
    (133, 3),
    (133, 4),
    (133, 5),
    (134, 1),
    (135, 1),
    (136, 2),
    (137, 2),
    (138, 2),
    (139, 2),
    (140, 2),
    (141, 2),
    (142, 2),
    (143, 5),
    (144, 2),
    (145, 2),
    (146, 2),
    (147, 2),
    (148, 2),
    (149, 2),
    (150, 2),
    (151, 2),
    (152, 2),
    (153, 2),
    (154, 2),
    (155, 2),
    (156, 2),
    (157, 2),
    (158, 2),
    (159, 2),
    (160, 2),
    (161, 2),
    (162, 2),
    (163, 2),
    (164, 1),
    (165, 3),
    (165, 4),
    (165, 5),
    (166, 3),
    (166, 4),
    (166, 5),
    (167, 2),
    (168, 2),
    (169, 2),
    (170, 2);

INSERT INTO Permissions (Permission_id, Name, Description)
VALUES
    (1, 'BAN_USERS', 'Ban users'),
    (2, 'BAN_USERNAMES', 'Ban usernames'),
    (3, 'DELETE_USERS', 'Delete users'),
    (4, 'UNBAN_USERS', 'Unban users'),
    (5, 'UNBAN_USERNAMES', 'Unban usernames');

INSERT INTO Roles (Role_id, Name, Description)
VALUES
    (1, 'ADMIN', 'Administrator'),
    (2, 'MODERATOR', 'Moderator'),
    (3, 'USER', 'User');

INSERT INTO Role_permissions (Role_id, Permission_id)
VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (1, 4),
    (1, 5),
    (2, 1),
    (2, 2),
    (2, 4),
    (2, 5);
