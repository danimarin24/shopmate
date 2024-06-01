-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: localhost    Database: ShopMate
-- ------------------------------------------------------
-- Server version	8.0.36-0ubuntu0.20.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Board`
--

DROP TABLE IF EXISTS `Board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Board` (
  `BoardId` int unsigned NOT NULL AUTO_INCREMENT,
  `Title` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `OwnerId` int unsigned NOT NULL,
  PRIMARY KEY (`BoardId`),
  KEY `IX_Board_OwnerId` (`OwnerId`),
  CONSTRAINT `Board_ibfk_1` FOREIGN KEY (`OwnerId`) REFERENCES `User` (`UserId`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Board`
--

LOCK TABLES `Board` WRITE;
/*!40000 ALTER TABLE `Board` DISABLE KEYS */;
INSERT INTO `Board` VALUES (11,'gym',50),(12,'test',1),(13,'comida fit',50),(14,'hacked',50),(15,'tauler1',51),(16,'provaTauler',51),(17,'cocina',52);
/*!40000 ALTER TABLE `Board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BoardCard`
--

DROP TABLE IF EXISTS `BoardCard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `BoardCard` (
  `BoardId` int unsigned NOT NULL,
  `CardId` int unsigned NOT NULL,
  PRIMARY KEY (`BoardId`,`CardId`),
  KEY `IX_BoardCard_CardId` (`CardId`),
  CONSTRAINT `BoardCard_ibfk_1` FOREIGN KEY (`BoardId`) REFERENCES `Board` (`BoardId`),
  CONSTRAINT `BoardCard_ibfk_2` FOREIGN KEY (`CardId`) REFERENCES `Card` (`CardId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BoardCard`
--

LOCK TABLES `BoardCard` WRITE;
/*!40000 ALTER TABLE `BoardCard` DISABLE KEYS */;
INSERT INTO `BoardCard` VALUES (12,1),(11,2),(14,3),(11,4),(13,7),(13,8),(15,10),(15,12),(17,14);
/*!40000 ALTER TABLE `BoardCard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Card`
--

DROP TABLE IF EXISTS `Card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Card` (
  `CardId` int unsigned NOT NULL AUTO_INCREMENT,
  `OwnerId` int unsigned NOT NULL,
  `IsPublic` bit(1) NOT NULL,
  `IsTemplate` bit(1) NOT NULL,
  `IsArchived` bit(1) NOT NULL,
  `EstimatedPrice` double unsigned DEFAULT NULL,
  `ColorId` int unsigned NOT NULL,
  `CardName` varchar(100) NOT NULL,
  PRIMARY KEY (`CardId`),
  UNIQUE KEY `Card_UNIQUE` (`OwnerId`,`CardName`),
  KEY `IX_Card_OwnerId` (`OwnerId`),
  KEY `IX_Card_ColorId` (`ColorId`),
  CONSTRAINT `Card_ibfk_1` FOREIGN KEY (`OwnerId`) REFERENCES `User` (`UserId`),
  CONSTRAINT `Card_ibfk_2` FOREIGN KEY (`ColorId`) REFERENCES `Color` (`ColorId`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Card`
--

LOCK TABLES `Card` WRITE;
/*!40000 ALTER TABLE `Card` DISABLE KEYS */;
INSERT INTO `Card` VALUES (1,1,_binary '',_binary '',_binary '\0',100,1,'olaahugo'),(2,50,_binary '',_binary '\0',_binary '\0',100,3,'Lista de dios'),(3,50,_binary '\0',_binary '\0',_binary '\0',100,4,'Lista de dios privada'),(4,50,_binary '',_binary '\0',_binary '\0',NULL,14,'test'),(7,50,_binary '',_binary '\0',_binary '\0',NULL,4,'comida'),(8,50,_binary '',_binary '\0',_binary '\0',NULL,11,'comida 2'),(10,51,_binary '',_binary '\0',_binary '\0',NULL,12,'prova'),(12,51,_binary '',_binary '\0',_binary '\0',NULL,3,''),(14,52,_binary '',_binary '\0',_binary '\0',NULL,3,'utensilios');
/*!40000 ALTER TABLE `Card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CardShareLink`
--

DROP TABLE IF EXISTS `CardShareLink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CardShareLink` (
  `Token` varchar(255) NOT NULL,
  `CardId` int unsigned NOT NULL,
  `RoleId` int unsigned NOT NULL,
  `Expiration` datetime DEFAULT NULL,
  PRIMARY KEY (`Token`),
  KEY `CardId` (`CardId`),
  KEY `RolId` (`RoleId`),
  CONSTRAINT `CardShareLink_ibfk_1` FOREIGN KEY (`CardId`) REFERENCES `Card` (`CardId`) ON DELETE CASCADE,
  CONSTRAINT `CardShareLink_ibfk_2` FOREIGN KEY (`RoleId`) REFERENCES `Role` (`RoleId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CardShareLink`
--

LOCK TABLES `CardShareLink` WRITE;
/*!40000 ALTER TABLE `CardShareLink` DISABLE KEYS */;
INSERT INTO `CardShareLink` VALUES ('C3t6-x1I2jZUk3le0hCYc9OdmAA',1,1,'2024-05-28 18:11:40'),('Dm-d9cVn3i8KCRTtPvcFz9RKub0',1,2,'2024-05-23 20:36:56'),('JNGaWoqAEY0BxeHwoHYAM0Xza-U',1,1,'2024-05-23 20:37:30'),('lwb_UTEJA3LqtlnH0hn-tJtBSro',1,2,'2024-05-23 18:46:00'),('nENtWiP7aOMbPcX7FDyuW7HFfEI',1,1,'2024-05-23 17:36:44');
/*!40000 ALTER TABLE `CardShareLink` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Category`
--

DROP TABLE IF EXISTS `Category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Category` (
  `CategoryId` int unsigned NOT NULL AUTO_INCREMENT,
  `Name` varchar(40) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `Icon` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `UpdatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `CreatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`CategoryId`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Category`
--

LOCK TABLES `Category` WRITE;
/*!40000 ALTER TABLE `Category` DISABLE KEYS */;
INSERT INTO `Category` VALUES (1,'Uncategorized','/api/images/category/uncategorized.png','2024-05-14 16:45:01','0001-01-01 00:00:00'),(2,'Lacteos','/api/images/category/lacteos.png','2024-05-14 16:45:01','2024-04-29 21:06:28'),(3,'Carnes','/api/images/category/carnes.png','2024-05-30 17:28:36','2024-05-30 17:28:36'),(4,'Pescados','/api/images/category/pescados.png','2024-05-30 17:28:36','2024-05-30 17:28:36'),(5,'Pasta','/api/images/category/pasta.png','2024-05-30 17:28:36','2024-05-30 17:28:36'),(6,'Cereales','/api/images/category/cereales.png','2024-05-30 17:29:20','2024-05-30 17:29:20'),(7,'Dulces','/api/images/category/dulces.png','2024-05-30 17:30:21','2024-05-30 17:30:21'),(8,'Bebidas','/api/images/category/bebidas.png','2024-05-30 17:30:21','2024-05-30 17:30:21'),(9,'Productos de hogar','/api/images/category/limpieza.png','2024-05-30 17:37:11','2024-05-30 17:37:11'),(10,'Productos para mascotas','/api/images/category/mascotas.png','2024-05-30 17:37:11','2024-05-30 17:37:11'),(11,'Productos propios','/api/images/category/404.png','2024-05-30 17:37:11','2024-05-30 17:37:11');
/*!40000 ALTER TABLE `Category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Color`
--

DROP TABLE IF EXISTS `Color`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Color` (
  `ColorId` int unsigned NOT NULL AUTO_INCREMENT,
  `ColorHex` varchar(6) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `ColorRed` smallint NOT NULL,
  `ColorGreen` smallint NOT NULL,
  `ColorBlue` smallint NOT NULL,
  `Name` varchar(30) NOT NULL,
  PRIMARY KEY (`ColorId`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Color`
--

LOCK TABLES `Color` WRITE;
/*!40000 ALTER TABLE `Color` DISABLE KEYS */;
INSERT INTO `Color` VALUES (1,'EADFB4',234,223,180,'Beige'),(2,'9BB0C1',155,176,193,'Light Blue'),(3,'51829B',81,130,155,'Blue'),(4,'F6995C',246,153,92,'Orange'),(5,'B5C18E',181,193,142,'Sage'),(6,'DEAC80',222,172,128,'Beige brown'),(7,'B99470',185,148,112,'Brown'),(8,'79AC78',121,172,120,'Green'),(9,'B0D9B1',176,217,177,'Light Green'),(10,'4793AF',71,147,175,'Blue water'),(11,'FFC470',255,196,112,'Light Orange'),(12,'DD5746',221,87,70,'Red'),(13,'8B322C',139,50,44,'Blood'),(14,'',0,0,0,'');
/*!40000 ALTER TABLE `Color` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Invoice`
--

DROP TABLE IF EXISTS `Invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Invoice` (
  `InvoiceId` int unsigned NOT NULL AUTO_INCREMENT,
  `CardId` int unsigned NOT NULL,
  `PaidBy` int unsigned NOT NULL,
  `TotalPrice` double unsigned NOT NULL,
  `PaidDate` datetime NOT NULL,
  `TicketImage` text NOT NULL,
  PRIMARY KEY (`InvoiceId`),
  KEY `IX_Invoice_CardId` (`CardId`),
  KEY `Invoice_User_FK` (`PaidBy`),
  CONSTRAINT `Invoice_ibfk_1` FOREIGN KEY (`CardId`) REFERENCES `Card` (`CardId`),
  CONSTRAINT `Invoice_User_FK` FOREIGN KEY (`PaidBy`) REFERENCES `User` (`UserId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Invoice`
--

LOCK TABLES `Invoice` WRITE;
/*!40000 ALTER TABLE `Invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `Invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `InvoiceLine`
--

DROP TABLE IF EXISTS `InvoiceLine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `InvoiceLine` (
  `InvoiceId` int unsigned NOT NULL AUTO_INCREMENT,
  `ItemId` int unsigned NOT NULL,
  `Amount` int NOT NULL,
  `Price` double unsigned NOT NULL,
  `CostXUnit` double unsigned NOT NULL,
  PRIMARY KEY (`InvoiceId`),
  KEY `IX_InvoiceLine_ItemId` (`ItemId`),
  CONSTRAINT `InvoiceLine_ibfk_1` FOREIGN KEY (`InvoiceId`) REFERENCES `Invoice` (`InvoiceId`),
  CONSTRAINT `InvoiceLine_ItemCardLine_FK` FOREIGN KEY (`ItemId`) REFERENCES `ItemCardLine` (`ItemCardLineId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `InvoiceLine`
--

LOCK TABLES `InvoiceLine` WRITE;
/*!40000 ALTER TABLE `InvoiceLine` DISABLE KEYS */;
/*!40000 ALTER TABLE `InvoiceLine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Item`
--

DROP TABLE IF EXISTS `Item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Item` (
  `ItemId` int unsigned NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `CategoryId` int unsigned NOT NULL,
  `UpdatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `CreatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ItemId`),
  KEY `IX_Item_CategoryId` (`CategoryId`),
  CONSTRAINT `Item_ibfk_1` FOREIGN KEY (`CategoryId`) REFERENCES `Category` (`CategoryId`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Item`
--

LOCK TABLES `Item` WRITE;
/*!40000 ALTER TABLE `Item` DISABLE KEYS */;
INSERT INTO `Item` VALUES (1,'Producto Sin Categoría 1',1,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(2,'Producto Sin Categoría 2',1,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(3,'Producto Sin Categoría 3',1,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(4,'Producto Sin Categoría 4',1,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(5,'Producto Sin Categoría 5',1,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(6,'Producto Sin Categoría 6',1,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(7,'Producto Sin Categoría 7',1,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(8,'Producto Sin Categoría 8',1,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(9,'Producto Sin Categoría 9',1,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(10,'Producto Sin Categoría 10',1,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(11,'Leche Entera',2,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(12,'Leche Descremada',2,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(13,'Queso Cheddar',2,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(14,'Queso Mozzarella',2,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(15,'Yogurt Natural',2,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(16,'Yogurt de Frutas',2,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(17,'Mantequilla',2,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(18,'Crema de Leche',2,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(19,'Queso Parmesano',2,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(20,'Leche de Almendras',2,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(21,'Carne de Res',3,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(22,'Carne de Cerdo',3,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(23,'Pollo',3,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(24,'Cordero',3,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(25,'Pavo',3,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(26,'Jamón',3,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(27,'Salchichas',3,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(28,'Tocino',3,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(29,'Costillas de Cerdo',3,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(30,'Carne Molida',3,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(31,'Salmón',4,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(32,'Atún',4,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(33,'Tilapia',4,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(34,'Trucha',4,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(35,'Bacalao',4,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(36,'Camarones',4,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(37,'Calamares',4,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(38,'Pulpo',4,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(39,'Mejillones',4,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(40,'Langosta',4,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(41,'Espaguetis',5,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(42,'Macarrones',5,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(43,'Fideos',5,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(44,'Lasaña',5,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(45,'Raviolis',5,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(46,'Tortellinis',5,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(47,'Canelones',5,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(48,'Ñoquis',5,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(49,'Fusilli',5,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(50,'Penne',5,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(51,'Avena',6,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(52,'Maíz Inflado',6,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(53,'Arroz Integral',6,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(54,'Quinoa',6,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(55,'Trigo Sarraceno',6,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(56,'Cebada',6,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(57,'Mijo',6,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(58,'Arroz Blanco',6,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(59,'Amaranto',6,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(60,'Sorgo',6,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(61,'Chocolate',7,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(62,'Caramelos',7,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(63,'Galletas',7,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(64,'Pasteles',7,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(65,'Helado',7,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(66,'Mermelada',7,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(67,'Turrón',7,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(68,'Donuts',7,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(69,'Chicles',7,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(70,'Marshmallows',7,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(71,'Coca-Cola',8,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(72,'Pepsi',8,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(73,'Agua Mineral',8,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(74,'Jugo de Naranja',8,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(75,'Jugo de Manzana',8,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(76,'Cerveza',8,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(77,'Vino Tinto',8,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(78,'Vino Blanco',8,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(79,'Whisky',8,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(80,'Vodka',8,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(81,'Detergente',9,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(82,'Suavizante',9,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(83,'Limpiavidrios',9,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(84,'Desinfectante',9,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(85,'Jabón Líquido',9,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(86,'Escobas',9,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(87,'Trapeadores',9,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(88,'Papel Higiénico',9,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(89,'Toallas de Papel',9,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(90,'Platos Desechables',9,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(91,'Comida para Perros',10,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(92,'Comida para Gatos',10,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(93,'Arena para Gatos',10,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(94,'Juguetes para Perros',10,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(95,'Juguetes para Gatos',10,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(96,'Snacks para Perros',10,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(97,'Snacks para Gatos',10,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(98,'Casas para Perros',10,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(99,'Rascadores para Gatos',10,'2024-05-30 18:22:58','2024-05-30 18:22:58'),(100,'Collares para Perros',10,'2024-05-30 18:22:58','2024-05-30 18:22:58');
/*!40000 ALTER TABLE `Item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ItemCardLine`
--

DROP TABLE IF EXISTS `ItemCardLine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ItemCardLine` (
  `ItemCardLineId` int unsigned NOT NULL AUTO_INCREMENT,
  `CardId` int unsigned NOT NULL,
  `CreatedBy` int unsigned NOT NULL,
  `AssignedTo` int unsigned NOT NULL,
  `Amount` int NOT NULL,
  `UnitId` int unsigned NOT NULL,
  `Price` double unsigned NOT NULL,
  `ItemId` int unsigned NOT NULL,
  PRIMARY KEY (`ItemCardLineId`),
  KEY `CardId` (`CardId`),
  KEY `CreatedBy` (`CreatedBy`),
  KEY `AssignedTo` (`AssignedTo`),
  KEY `ItemCardLine_Unit_FK` (`UnitId`),
  CONSTRAINT `ItemCardLine_ibfk_1` FOREIGN KEY (`CardId`) REFERENCES `Card` (`CardId`),
  CONSTRAINT `ItemCardLine_ibfk_2` FOREIGN KEY (`CreatedBy`) REFERENCES `User` (`UserId`),
  CONSTRAINT `ItemCardLine_ibfk_3` FOREIGN KEY (`AssignedTo`) REFERENCES `User` (`UserId`),
  CONSTRAINT `ItemCardLine_Unit_FK` FOREIGN KEY (`UnitId`) REFERENCES `Unit` (`UnitId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ItemCardLine`
--

LOCK TABLES `ItemCardLine` WRITE;
/*!40000 ALTER TABLE `ItemCardLine` DISABLE KEYS */;
/*!40000 ALTER TABLE `ItemCardLine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MembersFromCard`
--

DROP TABLE IF EXISTS `MembersFromCard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `MembersFromCard` (
  `CardId` int unsigned NOT NULL AUTO_INCREMENT,
  `UserId` int unsigned NOT NULL,
  `RoleId` int unsigned NOT NULL,
  PRIMARY KEY (`CardId`,`UserId`,`RoleId`),
  KEY `IX_MembersFromCard_UserId` (`UserId`),
  KEY `IX_MembersFromCard_RolId` (`RoleId`),
  CONSTRAINT `MembersFromCard_ibfk_1` FOREIGN KEY (`CardId`) REFERENCES `Card` (`CardId`),
  CONSTRAINT `MembersFromCard_ibfk_2` FOREIGN KEY (`UserId`) REFERENCES `User` (`UserId`),
  CONSTRAINT `MembersFromCard_ibfk_3` FOREIGN KEY (`RoleId`) REFERENCES `Role` (`RoleId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MembersFromCard`
--

LOCK TABLES `MembersFromCard` WRITE;
/*!40000 ALTER TABLE `MembersFromCard` DISABLE KEYS */;
INSERT INTO `MembersFromCard` VALUES (1,1,1),(1,1,2),(1,26,1),(1,50,1);
/*!40000 ALTER TABLE `MembersFromCard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Role`
--

DROP TABLE IF EXISTS `Role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Role` (
  `RoleId` int unsigned NOT NULL AUTO_INCREMENT,
  `RoleName` varchar(100) NOT NULL,
  PRIMARY KEY (`RoleId`),
  UNIQUE KEY `Role_UNIQUE` (`RoleName`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Role`
--

LOCK TABLES `Role` WRITE;
/*!40000 ALTER TABLE `Role` DISABLE KEYS */;
INSERT INTO `Role` VALUES (1,'Admin'),(11,'Collaborator'),(7,'Contributor'),(5,'Editor'),(10,'Guest'),(9,'Manager'),(8,'Owner'),(13,'Restricted'),(12,'Supervisor'),(2,'User'),(6,'Viewer');
/*!40000 ALTER TABLE `Role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SavedCard`
--

DROP TABLE IF EXISTS `SavedCard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SavedCard` (
  `UserId` int unsigned NOT NULL,
  `CardId` int unsigned NOT NULL,
  PRIMARY KEY (`UserId`,`CardId`),
  KEY `IX_SavedCard_CardId` (`CardId`),
  CONSTRAINT `SavedCard_ibfk_1` FOREIGN KEY (`UserId`) REFERENCES `User` (`UserId`),
  CONSTRAINT `SavedCard_ibfk_2` FOREIGN KEY (`CardId`) REFERENCES `Card` (`CardId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SavedCard`
--

LOCK TABLES `SavedCard` WRITE;
/*!40000 ALTER TABLE `SavedCard` DISABLE KEYS */;
/*!40000 ALTER TABLE `SavedCard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Setting`
--

DROP TABLE IF EXISTS `Setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Setting` (
  `SettingId` int unsigned NOT NULL AUTO_INCREMENT,
  `LastConnection` datetime NOT NULL,
  `IsOnline` bit(1) NOT NULL,
  `IsAdmin` bit(1) NOT NULL,
  `IsPrivate` bit(1) NOT NULL,
  `IsInfluencer` bit(1) NOT NULL,
  `LastPasswordChanged` datetime NOT NULL,
  `LastPasswordHash` text NOT NULL,
  PRIMARY KEY (`SettingId`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Setting`
--

LOCK TABLES `Setting` WRITE;
/*!40000 ALTER TABLE `Setting` DISABLE KEYS */;
INSERT INTO `Setting` VALUES (1,'2024-05-03 17:27:28',_binary '',_binary '',_binary '',_binary '','2024-05-03 17:27:28','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92'),(30,'2024-05-03 17:27:28',_binary '',_binary '\0',_binary '',_binary '\0','2024-05-03 17:27:28','b732b505142b184c1e53dc3601abc7896a6d27dc69bf372f2791a7780997cebb'),(31,'2024-05-07 19:15:38',_binary '',_binary '\0',_binary '',_binary '\0','2024-05-07 19:15:38','ywuwu'),(36,'2024-05-07 20:03:45',_binary '',_binary '\0',_binary '',_binary '\0','2024-05-07 20:03:45','123456'),(37,'2024-05-09 15:21:48',_binary '',_binary '\0',_binary '',_binary '\0','2024-05-09 15:21:48','aakkak'),(38,'2024-05-09 16:19:10',_binary '',_binary '\0',_binary '',_binary '\0','2024-05-09 16:19:10','test'),(39,'2024-05-13 16:58:56',_binary '',_binary '\0',_binary '',_binary '\0','2024-05-13 16:58:56','test'),(40,'2024-05-13 20:58:28',_binary '',_binary '\0',_binary '',_binary '\0','2024-05-13 20:58:28','DSAD'),(41,'2024-05-13 21:01:54',_binary '',_binary '\0',_binary '',_binary '\0','2024-05-13 21:01:54','dsadsa'),(43,'2024-05-13 21:07:43',_binary '',_binary '\0',_binary '',_binary '\0','2024-05-13 21:07:43','sksakask'),(44,'2024-05-14 17:34:00',_binary '',_binary '\0',_binary '',_binary '\0','2024-05-14 17:34:00','test'),(45,'2024-05-14 17:42:30',_binary '',_binary '\0',_binary '',_binary '\0','2024-05-14 17:42:30','123456789'),(46,'2024-05-17 16:22:47',_binary '',_binary '\0',_binary '',_binary '\0','2024-05-17 16:22:47','123456'),(47,'2024-05-21 15:14:22',_binary '',_binary '\0',_binary '',_binary '\0','2024-05-21 15:14:22','123456'),(48,'2024-05-21 15:15:39',_binary '',_binary '\0',_binary '',_binary '\0','2024-05-21 15:15:39','1'),(49,'2024-05-21 15:34:41',_binary '',_binary '\0',_binary '',_binary '\0','2024-05-21 15:34:41','6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b'),(50,'2024-05-21 16:08:43',_binary '',_binary '\0',_binary '',_binary '\0','2024-05-21 16:08:43','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3'),(52,'2024-05-21 16:14:26',_binary '',_binary '\0',_binary '',_binary '\0','2024-05-21 16:14:26','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3'),(54,'2024-05-21 16:21:06',_binary '',_binary '\0',_binary '',_binary '\0','2024-05-21 16:21:06','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3'),(55,'2024-05-21 16:23:52',_binary '',_binary '\0',_binary '',_binary '\0','2024-05-21 16:23:52','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3'),(56,'2024-05-27 19:30:08',_binary '',_binary '\0',_binary '',_binary '\0','2024-05-27 19:30:08','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92'),(57,'2024-05-27 19:57:58',_binary '',_binary '\0',_binary '',_binary '\0','2024-05-27 19:57:58','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3'),(58,'2024-05-27 20:27:23',_binary '',_binary '\0',_binary '',_binary '\0','2024-05-27 20:27:23','668ecd45a773fe343bf74848865e2f5e7d4f9100698fcd25fe111ce738237160'),(59,'2024-05-27 20:28:36',_binary '',_binary '\0',_binary '',_binary '\0','2024-05-27 20:28:36','16ecab1875791e2b6ed0c9a6dae5a12a79d92120e1c3afbd3a9c8535ce44666d');
/*!40000 ALTER TABLE `Setting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Unit`
--

DROP TABLE IF EXISTS `Unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Unit` (
  `UnitId` int unsigned NOT NULL AUTO_INCREMENT,
  `Name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `Prefix` varchar(6) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`UnitId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Unit`
--

LOCK TABLES `Unit` WRITE;
/*!40000 ALTER TABLE `Unit` DISABLE KEYS */;
INSERT INTO `Unit` VALUES (1,'Milligrams','mg'),(2,'Grams','g'),(3,'Kilograms','kg'),(4,'Liters','L'),(5,'Teaspoons','tsp'),(6,'Tablespoons','tbsp'),(7,'Cups','cup'),(8,'Ounces','oz');
/*!40000 ALTER TABLE `Unit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `User` (
  `UserId` int unsigned NOT NULL AUTO_INCREMENT,
  `Username` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `Name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `Password` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `Email` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `PhoneNumber` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `ProfileImage` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `GoogleToken` text,
  `FacebookToken` text,
  `RegisterDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `LastConnection` datetime DEFAULT CURRENT_TIMESTAMP,
  `SettingId` int unsigned NOT NULL,
  PRIMARY KEY (`UserId`),
  UNIQUE KEY `User_email_unique` (`Email`),
  UNIQUE KEY `User_username_unique` (`Username`),
  KEY `User_Setting_FK` (`SettingId`),
  CONSTRAINT `User_Setting_FK` FOREIGN KEY (`SettingId`) REFERENCES `Setting` (`SettingId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1,'danimarin','Dani Marin','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','dani4marin@gmail.com','693485264','/api/images/user/638513043795905625.jpeg',NULL,NULL,'2024-05-07 19:09:18','2024-05-07 19:09:18',1),(26,'tabletDani','dam.daw tablet',NULL,'dam.daw@iescarlesvallbona.cat','69348525','https://lh3.googleusercontent.com/a/ACg8ocLlOUd7lU-ao-1PLmzWRPD9xeBC3cQDPWmo1fAZNY05ez2wzA=s96-c','b732b505142b184c1e53dc3601abc7896a6d27dc69bf372f2791a7780997cebb',NULL,'2024-05-03 00:00:00','2024-05-03 00:00:00',30),(50,'dios','Martin','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3','magofer99@gmail.com','','/api/images/user/638513048400864796.jpg',NULL,NULL,'2024-05-21 16:23:51','2024-05-21 00:00:00',55),(51,'jordi ','Jordi ','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','jordi.soto@gmail.com','','',NULL,NULL,'2024-05-27 19:30:08','2024-05-27 00:00:00',56),(52,'martin12','martin','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3','martin@gma','','',NULL,NULL,'2024-05-27 19:57:58','2024-05-27 00:00:00',57),(53,'martin1234','martin','668ecd45a773fe343bf74848865e2f5e7d4f9100698fcd25fe111ce738237160','magofer00@gmail,com','6177218812','/api/images/user/638524384429253483.jpg',NULL,NULL,'2024-05-27 20:27:23','2024-05-27 20:27:19',58),(54,'dsasd','fdjshj','16ecab1875791e2b6ed0c9a6dae5a12a79d92120e1c3afbd3a9c8535ce44666d','dsadsa@gmail.com','763737','',NULL,NULL,'2024-05-27 20:28:37','2024-05-27 20:28:33',59);
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserFollower`
--

DROP TABLE IF EXISTS `UserFollower`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `UserFollower` (
  `UserId` int unsigned NOT NULL,
  `UserFollowed` int unsigned NOT NULL,
  PRIMARY KEY (`UserId`,`UserFollowed`),
  KEY `IX_UserFollower_UserFollowed` (`UserFollowed`),
  CONSTRAINT `UserFollower_ibfk_1` FOREIGN KEY (`UserId`) REFERENCES `User` (`UserId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `UserFollower_ibfk_2` FOREIGN KEY (`UserFollowed`) REFERENCES `User` (`UserId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserFollower`
--

LOCK TABLES `UserFollower` WRITE;
/*!40000 ALTER TABLE `UserFollower` DISABLE KEYS */;
INSERT INTO `UserFollower` VALUES (26,1),(1,26);
/*!40000 ALTER TABLE `UserFollower` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserItem`
--

DROP TABLE IF EXISTS `UserItem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `UserItem` (
  `UserItemId` int unsigned NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `CategoryId` int unsigned NOT NULL,
  `CreatorId` int unsigned NOT NULL,
  `UpdatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `CreatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`UserItemId`),
  KEY `IX_UserItem_CategoryId` (`CategoryId`),
  KEY `UserItem_ibfk_2` (`CreatorId`),
  CONSTRAINT `UserItem_ibfk_1` FOREIGN KEY (`CategoryId`) REFERENCES `Category` (`CategoryId`),
  CONSTRAINT `UserItem_ibfk_2` FOREIGN KEY (`CreatorId`) REFERENCES `User` (`UserId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserItem`
--

LOCK TABLES `UserItem` WRITE;
/*!40000 ALTER TABLE `UserItem` DISABLE KEYS */;
/*!40000 ALTER TABLE `UserItem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `UserStatistics`
--

DROP TABLE IF EXISTS `UserStatistics`;
/*!50001 DROP VIEW IF EXISTS `UserStatistics`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `UserStatistics` AS SELECT 
 1 AS `UserId`,
 1 AS `NFollows`,
 1 AS `NFollowers`,
 1 AS `NYourSaves`,
 1 AS `NSaves`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping routines for database 'ShopMate'
--

--
-- Final view structure for view `UserStatistics`
--

/*!50001 DROP VIEW IF EXISTS `UserStatistics`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `UserStatistics` AS select `u`.`UserId` AS `UserId`,(select count(`UserFollower`.`UserId`) from `UserFollower` where (`UserFollower`.`UserId` = `u`.`UserId`)) AS `NFollows`,(select count(`UserFollower`.`UserFollowed`) from `UserFollower` where (`UserFollower`.`UserFollowed` = `u`.`UserId`)) AS `NFollowers`,(select count(`SavedCard`.`UserId`) from `SavedCard` where (`SavedCard`.`UserId` = `u`.`UserId`)) AS `NYourSaves`,(select count(0) from (`SavedCard` `s` join `Card` `c` on((`s`.`CardId` = `c`.`CardId`))) where (`c`.`OwnerId` = `u`.`UserId`)) AS `NSaves` from (select distinct `UserFollower`.`UserId` AS `UserId` from `UserFollower` union select distinct `SavedCard`.`UserId` AS `UserId` from `SavedCard`) `u` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-01 18:13:36
