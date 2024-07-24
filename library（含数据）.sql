/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80033 (8.0.33)
 Source Host           : localhost:3306
 Source Schema         : library

 Target Server Type    : MySQL
 Target Server Version : 80033 (8.0.33)
 File Encoding         : 65001

 Date: 24/07/2024 00:04:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `bname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `tid` int NULL DEFAULT NULL,
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '作者',
  `press` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '出版社',
  `btimes` int NULL DEFAULT 0 COMMENT '被借阅次数',
  `introduce` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `poster` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_tid_book`(`tid` ASC) USING BTREE,
  CONSTRAINT `fk_tid_book` FOREIGN KEY (`tid`) REFERENCES `type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 122 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of book
-- ----------------------------

-- ----------------------------
-- Table structure for history
-- ----------------------------
DROP TABLE IF EXISTS `history`;
CREATE TABLE `history`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `uid` int NULL DEFAULT NULL,
  `bid` int NULL DEFAULT NULL,
  `wid` int NULL DEFAULT NULL,
  `begin_time` datetime NULL DEFAULT NULL,
  `end_time` datetime NULL DEFAULT NULL,
  `times` int NULL DEFAULT NULL COMMENT '记录续借次数',
  `status` int NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_uid_history`(`uid` ASC) USING BTREE,
  INDEX `fk_bid_history`(`bid` ASC) USING BTREE,
  INDEX `fk_wid_history`(`wid` ASC) USING BTREE,
  CONSTRAINT `fk_bid_history` FOREIGN KEY (`bid`) REFERENCES `book` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_uid_history` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_wid_history` FOREIGN KEY (`wid`) REFERENCES `warehouse` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1286 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of history
-- ----------------------------

-- ----------------------------
-- Table structure for inventory
-- ----------------------------
DROP TABLE IF EXISTS `inventory`;
CREATE TABLE `inventory`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `bid` int NULL DEFAULT NULL,
  `wid` int NULL DEFAULT NULL,
  `quantity` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_bid_inventory`(`bid` ASC) USING BTREE,
  INDEX `fk_wid_inventory`(`wid` ASC) USING BTREE,
  CONSTRAINT `fk_bid_inventory` FOREIGN KEY (`bid`) REFERENCES `book` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_wid_inventory` FOREIGN KEY (`wid`) REFERENCES `warehouse` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 198 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of inventory
-- ----------------------------

-- ----------------------------
-- Table structure for stockin
-- ----------------------------
DROP TABLE IF EXISTS `stockin`;
CREATE TABLE `stockin`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `bid` int NULL DEFAULT NULL,
  `wid` int NULL DEFAULT NULL,
  `quantity` int NULL DEFAULT NULL COMMENT '入库数',
  `createtime` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_bid_stockin`(`bid` ASC) USING BTREE,
  INDEX `fk_wid_stockin`(`wid` ASC) USING BTREE,
  CONSTRAINT `fk_bid_stockin` FOREIGN KEY (`bid`) REFERENCES `book` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_wid_stockin` FOREIGN KEY (`wid`) REFERENCES `warehouse` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 65 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of stockin
-- ----------------------------

-- ----------------------------
-- Table structure for type
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `tname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of type
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `utimes` int NULL DEFAULT 0 COMMENT '借阅次数',
  `max_num` int NULL DEFAULT 3 COMMENT '最大借阅数',
  `max_times` int NULL DEFAULT 1 COMMENT '最大延长借阅次数',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'USER',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
  `joined_date` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '小行星', '18877445369', 'admin123', 126, 3, 1, 'ROOT', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', '2024-07-20 18:29:02');
INSERT INTO `user` VALUES (2, '大将军', '19925927806', 'admin123', 0, 3, 1, 'USER', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', '2024-07-20 21:04:11');
INSERT INTO `user` VALUES (4, '小宝贝', '19929992322', 'admin123', 0, 3, 1, 'USER', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', '2024-07-21 08:16:41');
INSERT INTO `user` VALUES (5, 'Heung Kwok Yin', '13307292006', 'fXnBVe8vOX', 0, 3, 1, 'USER', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', '2007-04-03 18:05:16');
INSERT INTO `user` VALUES (6, 'Pan Yunxi', '15502257428', 'axp0mYNaUZ', 0, 3, 1, 'ROOT', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', '2014-03-09 23:40:25');

-- ----------------------------
-- Table structure for warehouse
-- ----------------------------
DROP TABLE IF EXISTS `warehouse`;
CREATE TABLE `warehouse`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of warehouse
-- ----------------------------

-- ----------------------------
-- View structure for book_monthly_borrow_view
-- ----------------------------
DROP VIEW IF EXISTS `book_monthly_borrow_view`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `book_monthly_borrow_view` AS select `b`.`id` AS `id`,`b`.`bname` AS `bname`,`b`.`author` AS `author`,`b`.`press` AS `press`,sum((case when (month(`h`.`begin_time`) = month(curdate())) then 1 else 0 end)) AS `d`,sum((case when (month(`h`.`begin_time`) = (month(curdate()) - 1)) then 1 else 0 end)) AS `c`,sum((case when (month(`h`.`begin_time`) = (month(curdate()) - 2)) then 1 else 0 end)) AS `b`,sum((case when (month(`h`.`begin_time`) = (month(curdate()) - 3)) then 1 else 0 end)) AS `a` from (`history` `h` join `book` `b` on((`h`.`bid` = `b`.`id`))) where (`h`.`begin_time` >= (curdate() - interval 3 month)) group by `b`.`id` order by (((`a` + `b`) + `c`) + `d`) desc;

-- ----------------------------
-- Triggers structure for table book
-- ----------------------------
DROP TRIGGER IF EXISTS `after_inventory_book_insert`;
delimiter ;;
CREATE TRIGGER `after_inventory_book_insert` AFTER INSERT ON `book` FOR EACH ROW BEGIN
    -- 检查库存表中是否已存在该书籍的库存记录
    DECLARE count_rows INT;
    SELECT COUNT(*) INTO count_rows
    FROM inventory
    WHERE bid = NEW.id;

    -- 如果库存表中不存在，则插入新的库存记录
    IF count_rows = 0 THEN
        INSERT INTO inventory (bid, wid, quantity)
        SELECT NEW.id, w.id, 0
        FROM warehouse w;
    END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table book
-- ----------------------------
DROP TRIGGER IF EXISTS `before_stockin_book_delete`;
delimiter ;;
CREATE TRIGGER `before_stockin_book_delete` BEFORE DELETE ON `book` FOR EACH ROW BEGIN
    DELETE FROM stockin
    WHERE bid = OLD.id;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table book
-- ----------------------------
DROP TRIGGER IF EXISTS `before_inventory_book_delete`;
delimiter ;;
CREATE TRIGGER `before_inventory_book_delete` BEFORE DELETE ON `book` FOR EACH ROW BEGIN
    -- 删除库存表中与被删除书籍相关的所有库存记录
    DELETE FROM inventory
    WHERE bid = OLD.id;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table history
-- ----------------------------
DROP TRIGGER IF EXISTS `after_inventory_history_insert`;
delimiter ;;
CREATE TRIGGER `after_inventory_history_insert` AFTER INSERT ON `history` FOR EACH ROW BEGIN
    IF NEW.status = 0 THEN
        UPDATE inventory
        SET quantity = quantity - 1
        WHERE bid = NEW.bid AND wid = NEW.wid;
    END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table history
-- ----------------------------
DROP TRIGGER IF EXISTS `after_history_times_insert`;
delimiter ;;
CREATE TRIGGER `after_history_times_insert` AFTER INSERT ON `history` FOR EACH ROW BEGIN
    -- 更新book表中对应bid的借阅次数加一
    UPDATE book
    SET btimes = btimes + 1
    WHERE id = NEW.bid;

    -- 更新user表中对应uid的借阅次数加一
    UPDATE user
    SET utimes = utimes + 1
    WHERE id = NEW.uid;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table history
-- ----------------------------
DROP TRIGGER IF EXISTS `after_inventory_history_status_update`;
delimiter ;;
CREATE TRIGGER `after_inventory_history_status_update` AFTER UPDATE ON `history` FOR EACH ROW BEGIN
    IF NEW.status = 1 AND OLD.status = 0 THEN
        UPDATE inventory
        SET quantity = quantity + 1
        WHERE bid = NEW.bid AND wid = NEW.wid;
    END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table history
-- ----------------------------
DROP TRIGGER IF EXISTS `after_inventory_history_delete`;
delimiter ;;
CREATE TRIGGER `after_inventory_history_delete` AFTER DELETE ON `history` FOR EACH ROW BEGIN
    IF OLD.status = 0 THEN
        UPDATE inventory
        SET quantity = quantity + 1
        WHERE bid = OLD.bid AND wid = OLD.wid;
    END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table stockin
-- ----------------------------
DROP TRIGGER IF EXISTS `after_inventory_stockin_insert`;
delimiter ;;
CREATE TRIGGER `after_inventory_stockin_insert` AFTER INSERT ON `stockin` FOR EACH ROW BEGIN
    DECLARE current_quantity INT;
    
    -- 获取当前仓库中这种书籍的库存量
    SELECT quantity INTO current_quantity
    FROM inventory
    WHERE bid = NEW.bid AND wid = NEW.wid;
    
    -- 如果记录存在，则更新库存量
    IF FOUND_ROWS() > 0 THEN
        UPDATE inventory
        SET quantity = quantity + NEW.quantity
        WHERE bid = NEW.bid AND wid = NEW.wid;
    ELSE
        -- 否则，插入新的库存记录
        INSERT INTO inventory (bid, wid, quantity)
        VALUES (NEW.bid, NEW.wid, NEW.quantity);
    END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table stockin
-- ----------------------------
DROP TRIGGER IF EXISTS `before_inventory_stockin_delete`;
delimiter ;;
CREATE TRIGGER `before_inventory_stockin_delete` BEFORE DELETE ON `stockin` FOR EACH ROW BEGIN
    DECLARE current_quantity INT;
    
    -- 获取当前仓库中这种书籍的库存量
    SELECT quantity INTO current_quantity
    FROM inventory
    WHERE bid = OLD.bid AND wid = OLD.wid;
    
    -- 如果记录存在，则更新库存量
    IF FOUND_ROWS() > 0 THEN
        UPDATE inventory
        SET quantity = quantity - OLD.quantity
        WHERE bid = OLD.bid AND wid = OLD.wid;
    END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table type
-- ----------------------------
DROP TRIGGER IF EXISTS `before_book_type_delete`;
delimiter ;;
CREATE TRIGGER `before_book_type_delete` BEFORE DELETE ON `type` FOR EACH ROW BEGIN
    DELETE FROM book
    WHERE tid = OLD.id;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table user
-- ----------------------------
DROP TRIGGER IF EXISTS `before_history_user_delete`;
delimiter ;;
CREATE TRIGGER `before_history_user_delete` BEFORE DELETE ON `user` FOR EACH ROW BEGIN
    -- 删除与用户相关的历史记录
    DELETE FROM history WHERE uid = OLD.id;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table warehouse
-- ----------------------------
DROP TRIGGER IF EXISTS `after_inventory_warehouse_insert`;
delimiter ;;
CREATE TRIGGER `after_inventory_warehouse_insert` AFTER INSERT ON `warehouse` FOR EACH ROW BEGIN
    -- 检查库存表中是否已存在该仓库的库存记录
    DECLARE count_rows INT;
    SELECT COUNT(*) INTO count_rows
    FROM inventory
    WHERE wid = NEW.id;

    -- 如果库存表中不存在，则插入新的库存记录
    IF count_rows = 0 THEN
        INSERT INTO inventory (bid, wid, quantity)
        SELECT b.id, NEW.id, 0
        FROM book b;
    END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table warehouse
-- ----------------------------
DROP TRIGGER IF EXISTS `before_inventory_warehouse_delete`;
delimiter ;;
CREATE TRIGGER `before_inventory_warehouse_delete` BEFORE DELETE ON `warehouse` FOR EACH ROW BEGIN
    -- 删除库存表中与被删除仓库相关的所有库存记录
    DELETE FROM inventory
    WHERE wid = OLD.id;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table warehouse
-- ----------------------------
DROP TRIGGER IF EXISTS `before_stockin_warehouse_delete`;
delimiter ;;
CREATE TRIGGER `before_stockin_warehouse_delete` BEFORE DELETE ON `warehouse` FOR EACH ROW BEGIN
    DELETE FROM stockin
    WHERE wid = OLD.id;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
