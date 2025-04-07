CREATE TABLE theme(
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(256) NOT NULL
);

CREATE TABLE lucky_love_love_Choices(
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    choice VARCHAR(256) NOT NULL,
    theme_id INTEGER NOT NULL,
    FOREIGN KEY (theme_id) REFERENCES theme(id) ON DELETE CASCADE
);
--ON DELETE CASCADEは紐づいたthemeが削除されるとChoicesのデータも削除されるようになる

CREATE TABLE users(
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(256) NOT NULL,
    password VARCHAR(256) NOT NULL
);

CREATE TABLE user_roles (
    user_id INTEGER NOT NULL,
    role VARCHAR(256) NOT NULL,
    PRIMARY KEY (user_id, role), -- user_id と role の組み合わせを主キーにする
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
