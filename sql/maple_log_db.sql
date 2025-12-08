-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               11.8.2-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.10.0.7000
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for maple_log
DROP DATABASE IF EXISTS `maple_log`;
CREATE DATABASE IF NOT EXISTS `maple_log` /*!40100 DEFAULT CHARACTER SET euckr COLLATE euckr_korean_ci */;
USE `maple_log`;

-- Dumping structure for table maple_log.auction_log
DROP TABLE IF EXISTS `auction_log`;
CREATE TABLE IF NOT EXISTS `auction_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) DEFAULT 0,
  `item_name` varchar(50) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `quantity` int(3) DEFAULT 0,
  `price` bigint(20) unsigned DEFAULT NULL,
  `serial_number` bigint(20) DEFAULT 0,
  `name` varchar(15) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `account_name` varchar(50) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `player_id` int(11) DEFAULT 0,
  `account_id` int(11) DEFAULT 0,
  `log` text CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `type` tinyint(3) DEFAULT 0,
  `time` timestamp NULL DEFAULT NULL,
  `bot` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `item_id` (`item_id`),
  KEY `price` (`price`),
  KEY `type` (`type`),
  KEY `player_id` (`player_id`),
  KEY `account_id` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci CHECKSUM=1;

-- Dumping data for table maple_log.auction_log: ~0 rows (approximately)
DELETE FROM `auction_log`;

-- Dumping structure for table maple_log.boss_log
DROP TABLE IF EXISTS `boss_log`;
CREATE TABLE IF NOT EXISTS `boss_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(13) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `account_name` varchar(50) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `player_id` int(11) DEFAULT 0,
  `account_id` int(11) DEFAULT 0,
  `log` text CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `type` tinyint(3) DEFAULT 0,
  `time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `player_id` (`player_id`),
  KEY `account_id` (`account_id`),
  KEY `time` (`time`),
  KEY `type` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table maple_log.boss_log: ~0 rows (approximately)
DELETE FROM `boss_log`;

-- Dumping structure for table maple_log.cabinet_log
DROP TABLE IF EXISTS `cabinet_log`;
CREATE TABLE IF NOT EXISTS `cabinet_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) DEFAULT 0,
  `quantity` int(3) DEFAULT 0,
  `name` varchar(15) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `account_name` varchar(50) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `player_id` int(11) DEFAULT 0,
  `account_id` int(11) DEFAULT 0,
  `log` text CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `type` tinyint(3) DEFAULT 0,
  `time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `KEY` (`account_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table maple_log.cabinet_log: ~0 rows (approximately)
DELETE FROM `cabinet_log`;

-- Dumping structure for table maple_log.connect_log
DROP TABLE IF EXISTS `connect_log`;
CREATE TABLE IF NOT EXISTS `connect_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(13) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `account_name` varchar(50) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `player_id` int(11) DEFAULT 0,
  `account_id` int(11) DEFAULT 0,
  `mac` varchar(50) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `volume` varchar(50) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `log` varchar(150) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `type` tinyint(3) DEFAULT 0,
  `time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `AccID` (`account_id`) USING BTREE,
  KEY `type` (`type`),
  KEY `player_id` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci CHECKSUM=1 ROW_FORMAT=DYNAMIC;

-- Dumping data for table maple_log.connect_log: ~0 rows (approximately)
DELETE FROM `connect_log`;

-- Dumping structure for table maple_log.consume_log
DROP TABLE IF EXISTS `consume_log`;
CREATE TABLE IF NOT EXISTS `consume_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(8) DEFAULT NULL,
  `name` varchar(13) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `account_name` varchar(50) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `player_id` int(11) DEFAULT 0,
  `account_id` int(11) DEFAULT 0,
  `log` text CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `PlayerID` (`player_id`) USING BTREE,
  KEY `account_id` (`account_id`),
  KEY `item_id` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table maple_log.consume_log: ~0 rows (approximately)
DELETE FROM `consume_log`;

-- Dumping structure for table maple_log.create_char_log
DROP TABLE IF EXISTS `create_char_log`;
CREATE TABLE IF NOT EXISTS `create_char_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(13) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `account_name` varchar(50) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `player_id` int(11) DEFAULT 0,
  `account_id` int(11) DEFAULT 0,
  `type` tinyint(3) DEFAULT 0,
  `log` text CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `KEY` (`account_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table maple_log.create_char_log: ~0 rows (approximately)
DELETE FROM `create_char_log`;

-- Dumping structure for table maple_log.custom_log
DROP TABLE IF EXISTS `custom_log`;
CREATE TABLE IF NOT EXISTS `custom_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(13) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `account_name` varchar(50) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `player_id` int(11) DEFAULT 0,
  `account_id` int(11) DEFAULT 0,
  `log` text CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `type` tinyint(3) DEFAULT 0,
  `time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `AccID` (`account_id`) USING BTREE,
  KEY `type` (`type`) USING BTREE,
  KEY `player_id` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table maple_log.custom_log: ~0 rows (approximately)
DELETE FROM `custom_log`;

-- Dumping structure for table maple_log.damage_hack_log
DROP TABLE IF EXISTS `damage_hack_log`;
CREATE TABLE IF NOT EXISTS `damage_hack_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(13) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `account_name` varchar(50) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `player_id` int(11) DEFAULT 0,
  `account_id` int(11) DEFAULT 0,
  `damage` bigint(20) DEFAULT 0,
  `log` text CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `KEY` (`account_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table maple_log.damage_hack_log: ~0 rows (approximately)
DELETE FROM `damage_hack_log`;

-- Dumping structure for table maple_log.donation_log
DROP TABLE IF EXISTS `donation_log`;
CREATE TABLE IF NOT EXISTS `donation_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `price` varchar(50) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `log` text CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `account_name` varchar(50) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `player_id` int(11) DEFAULT 0,
  `account_id` int(11) DEFAULT 0,
  `time` timestamp NULL DEFAULT NULL,
  `bot` int(11) DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `KEY` (`account_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13359 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table maple_log.donation_log: ~0 rows (approximately)
DELETE FROM `donation_log`;

-- Dumping structure for table maple_log.drop_log
DROP TABLE IF EXISTS `drop_log`;
CREATE TABLE IF NOT EXISTS `drop_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) DEFAULT 0,
  `quantity` int(3) DEFAULT 0,
  `channel` tinyint(4) DEFAULT 0,
  `field_id` int(9) DEFAULT 0,
  `name` varchar(15) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `account_name` varchar(20) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `player_id` int(11) DEFAULT 0,
  `account_id` int(11) DEFAULT 0,
  `log` text CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `type` tinyint(3) DEFAULT 0,
  `time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `PlayerID` (`player_id`) USING BTREE,
  KEY `account_id` (`account_id`),
  KEY `type` (`type`),
  KEY `item_id` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- Dumping data for table maple_log.drop_log: ~0 rows (approximately)
DELETE FROM `drop_log`;

-- Dumping structure for table maple_log.enchant_log
DROP TABLE IF EXISTS `enchant_log`;
CREATE TABLE IF NOT EXISTS `enchant_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) DEFAULT 0,
  `target_item_id` int(11) DEFAULT 0,
  `target_serial_number` bigint(20) DEFAULT 0,
  `name` varchar(15) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `account_name` varchar(50) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `player_id` int(11) DEFAULT 0,
  `account_id` int(11) DEFAULT 0,
  `log` text CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `type` tinyint(3) DEFAULT NULL,
  `result` tinyint(3) DEFAULT NULL,
  `time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `TYPE` (`type`) USING BTREE,
  KEY `PlayerID` (`player_id`) USING BTREE,
  KEY `account_id` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table maple_log.enchant_log: ~0 rows (approximately)
DELETE FROM `enchant_log`;

-- Dumping structure for table maple_log.hack_log
DROP TABLE IF EXISTS `hack_log`;
CREATE TABLE IF NOT EXISTS `hack_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `account_name` varchar(50) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `player_id` int(11) DEFAULT 0,
  `account_id` int(11) DEFAULT 0,
  `log` text CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `type` tinyint(3) DEFAULT 0,
  `time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `KEY` (`account_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table maple_log.hack_log: ~0 rows (approximately)
DELETE FROM `hack_log`;

-- Dumping structure for table maple_log.item_log
DROP TABLE IF EXISTS `item_log`;
CREATE TABLE IF NOT EXISTS `item_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) DEFAULT 0,
  `quantity` int(3) DEFAULT 0,
  `channel` tinyint(4) DEFAULT 0,
  `field_id` int(9) DEFAULT 0,
  `serial_number` bigint(11) DEFAULT NULL,
  `name` varchar(15) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `account_name` varchar(50) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `player_id` int(11) DEFAULT 0,
  `account_id` int(11) DEFAULT 0,
  `log` text CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `type` tinyint(3) DEFAULT 0,
  `price` bigint(20) DEFAULT NULL,
  `time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `PlayerID` (`player_id`) USING BTREE,
  KEY `TYPE` (`type`) USING BTREE,
  KEY `AccID` (`account_id`) USING BTREE,
  KEY `item_id` (`item_id`),
  KEY `serial_number` (`serial_number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- Dumping data for table maple_log.item_log: ~0 rows (approximately)
DELETE FROM `item_log`;

-- Dumping structure for table maple_log.macro_log
DROP TABLE IF EXISTS `macro_log`;
CREATE TABLE IF NOT EXISTS `macro_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(15) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `account_name` varchar(50) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `player_id` int(11) DEFAULT 0,
  `account_id` int(11) DEFAULT 0,
  `log` text CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `type` int(3) DEFAULT 0,
  `result` int(3) DEFAULT 0,
  `time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `KEY` (`account_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table maple_log.macro_log: ~0 rows (approximately)
DELETE FROM `macro_log`;

-- Dumping structure for table maple_log.pickup_log
DROP TABLE IF EXISTS `pickup_log`;
CREATE TABLE IF NOT EXISTS `pickup_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) DEFAULT 0,
  `quantity` int(3) DEFAULT 0,
  `channel` tinyint(4) DEFAULT 0,
  `field_id` int(9) DEFAULT 0,
  `serial_number` bigint(11) DEFAULT NULL,
  `name` varchar(15) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `account_name` varchar(50) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `player_id` int(11) DEFAULT 0,
  `account_id` int(11) DEFAULT 0,
  `log` text CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `type` tinyint(3) DEFAULT 0,
  `price` bigint(20) DEFAULT NULL,
  `time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `PlayerID` (`player_id`) USING BTREE,
  KEY `TYPE` (`type`) USING BTREE,
  KEY `AccID` (`account_id`) USING BTREE,
  KEY `item_id` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- Dumping data for table maple_log.pickup_log: ~0 rows (approximately)
DELETE FROM `pickup_log`;

-- Dumping structure for table maple_log.trade_log
DROP TABLE IF EXISTS `trade_log`;
CREATE TABLE IF NOT EXISTS `trade_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(15) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `player_id` int(11) DEFAULT 0,
  `account_id` int(11) DEFAULT 0,
  `name2` varchar(15) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `player_id2` int(11) DEFAULT 0,
  `account_id2` int(11) DEFAULT 0,
  `log` text CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `type` tinyint(3) DEFAULT 0,
  `time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `TYPE` (`type`) USING BTREE,
  KEY `PlayerID` (`player_id`,`player_id2`) USING BTREE,
  KEY `account_id` (`account_id`,`account_id2`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- Dumping data for table maple_log.trade_log: ~0 rows (approximately)
DELETE FROM `trade_log`;

-- Dumping structure for table maple_log.transfer_field_log
DROP TABLE IF EXISTS `transfer_field_log`;
CREATE TABLE IF NOT EXISTS `transfer_field_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(13) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `account_name` varchar(50) CHARACTER SET euckr COLLATE euckr_bin DEFAULT NULL,
  `player_id` int(11) DEFAULT 0,
  `account_id` int(11) DEFAULT 0,
  `current_field` int(11) DEFAULT 0,
  `target_field` int(11) DEFAULT 0,
  `time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `account_id` (`account_id`),
  KEY `player_id` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table maple_log.transfer_field_log: ~0 rows (approximately)
DELETE FROM `transfer_field_log`;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
