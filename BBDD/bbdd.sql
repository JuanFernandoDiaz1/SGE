-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 09-02-2021 a las 16:22:38
-- Versión del servidor: 10.4.14-MariaDB
-- Versión de PHP: 7.4.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bbdd`
--
CREATE DATABASE IF NOT EXISTS `bbdd` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `bbdd`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `ID_Cliente` int(11) NOT NULL,
  `DNI` varchar(9) NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `Direccion` varchar(200) NOT NULL,
  `Email` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`ID_Cliente`, `DNI`, `Nombre`, `Direccion`, `Email`) VALUES
(27, '09130325Z', 'Mario', 'Calle 1', 'juan@gmail.com');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `compra`
--

CREATE TABLE `compra` (
  `Factura` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `id_personal` int(11) NOT NULL,
  `id_proveedor` int(11) NOT NULL,
  `PrecioCompra` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `compra`
--

INSERT INTO `compra` (`Factura`, `fecha`, `id_personal`, `id_proveedor`, `PrecioCompra`) VALUES
(17, '2019-01-01', 6, 4, 475),
(18, '2019-12-09', 6, 4, 4750);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `escandallo`
--

CREATE TABLE `escandallo` (
  `ID_Escandallo` int(11) NOT NULL,
  `ID_Producto` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `escandallo`
--

INSERT INTO `escandallo` (`ID_Escandallo`, `ID_Producto`) VALUES
(7, 24);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `escandallos_materiales`
--

CREATE TABLE `escandallos_materiales` (
  `ID_Escandallo` int(11) NOT NULL,
  `ID_Material` int(11) NOT NULL,
  `UnidadesMaterial` int(11) NOT NULL,
  `ID_Escandallo_materiales` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `escandallos_materiales`
--

INSERT INTO `escandallos_materiales` (`ID_Escandallo`, `ID_Material`, `UnidadesMaterial`, `ID_Escandallo_materiales`) VALUES
(7, 20, 1, 14),
(7, 21, 1, 15),
(7, 22, 1, 16),
(7, 23, 1, 17);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `materiales`
--

CREATE TABLE `materiales` (
  `ID_Material` int(11) NOT NULL,
  `Nombre` int(11) NOT NULL,
  `Stock` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ordenesfabrica`
--

CREATE TABLE `ordenesfabrica` (
  `Unidades` int(11) NOT NULL,
  `ID_Escandallo` int(11) NOT NULL,
  `ID_orden` int(11) NOT NULL,
  `ID_Personal` int(11) NOT NULL,
  `FechaInicio` date NOT NULL,
  `FechaFin` date NOT NULL,
  `Estado` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `ordenesfabrica`
--

INSERT INTO `ordenesfabrica` (`Unidades`, `ID_Escandallo`, `ID_orden`, `ID_Personal`, `FechaInicio`, `FechaFin`, `Estado`) VALUES
(2, 7, 8, 6, '2019-02-13', '2019-02-25', 'Acabado'),
(4, 7, 9, 6, '2020-02-09', '2020-02-15', 'Acabado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personal`
--

CREATE TABLE `personal` (
  `Nombre` varchar(100) NOT NULL,
  `DNI` varchar(9) NOT NULL,
  `ID_Personal` int(11) NOT NULL,
  `Direccion` varchar(200) NOT NULL,
  `Email` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `personal`
--

INSERT INTO `personal` (`Nombre`, `DNI`, `ID_Personal`, `Direccion`, `Email`) VALUES
('Rafael', '08888888X', 6, 'calle', 'rafa@gmail.com');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `Nombre` varchar(100) NOT NULL,
  `Descripcion` varchar(500) NOT NULL,
  `Precio` int(11) NOT NULL,
  `PrecioVenta` int(11) NOT NULL,
  `Stock` int(11) NOT NULL,
  `ID_Producto` int(11) NOT NULL,
  `ID_Proveedor` int(11) NOT NULL,
  `tipo` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`Nombre`, `Descripcion`, `Precio`, `PrecioVenta`, `Stock`, `ID_Producto`, `ID_Proveedor`, `tipo`) VALUES
('placa base', 'placa', 100, 150, 9, 20, 5, 'Simple'),
('procesador', 'pro', 200, 250, 9, 21, 5, 'Simple'),
('memoria ram', 'mr', 160, 210, 9, 22, 5, 'Simple'),
('raton', 'ra', 15, 25, 11, 23, 5, 'Simple'),
('pc', 'pc flamigero', 460, 900, 5, 24, 5, 'Compuesto');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos_compra`
--

CREATE TABLE `productos_compra` (
  `ID` int(11) NOT NULL,
  `id_producto` int(11) NOT NULL,
  `Id_compra` int(11) NOT NULL,
  `Unidades` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `productos_compra`
--

INSERT INTO `productos_compra` (`ID`, `id_producto`, `Id_compra`, `Unidades`) VALUES
(44, 22, 17, 1),
(45, 20, 17, 1),
(46, 21, 17, 1),
(47, 23, 17, 1),
(48, 22, 18, 10),
(49, 20, 18, 10),
(50, 21, 18, 10),
(51, 23, 18, 10);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos_ventas`
--

CREATE TABLE `productos_ventas` (
  `ID` int(11) NOT NULL,
  `ID_Producto` int(11) NOT NULL,
  `ID_Venta` int(11) NOT NULL,
  `Unidades` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `productos_ventas`
--

INSERT INTO `productos_ventas` (`ID`, `ID_Producto`, `ID_Venta`, `Unidades`) VALUES
(31, 22, 28, 1),
(32, 20, 28, 1),
(33, 21, 28, 1),
(34, 20, 29, 1),
(35, 21, 29, 1),
(36, 22, 29, 1),
(37, 24, 30, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedores`
--

CREATE TABLE `proveedores` (
  `Nombre` varchar(100) NOT NULL,
  `NIF` varchar(13) NOT NULL,
  `Direccion` varchar(200) NOT NULL,
  `Email` varchar(200) NOT NULL,
  `ID_Proveedor` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `proveedores`
--

INSERT INTO `proveedores` (`Nombre`, `NIF`, `Direccion`, `Email`, `ID_Proveedor`) VALUES
('Agustin', '012012012X', 'Calle 1', 'jordan@gmail.com', 4),
('DiWi phones', '123123123123A', 'Calle marioooATM', 'diwi@phones.kipas', 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedores_productos`
--

CREATE TABLE `proveedores_productos` (
  `ID` int(11) NOT NULL,
  `ID_Poducto` int(11) NOT NULL,
  `ID_Proveedor` int(11) NOT NULL,
  `Unidades` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `telefonos`
--

CREATE TABLE `telefonos` (
  `ID_Telefono` int(11) NOT NULL,
  `Numero` int(11) NOT NULL,
  `ID_Proveedor` int(11) DEFAULT NULL,
  `ID_Personal` int(11) DEFAULT NULL,
  `ID_Cliente` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `telefonos`
--

INSERT INTO `telefonos` (`ID_Telefono`, `Numero`, `ID_Proveedor`, `ID_Personal`, `ID_Cliente`) VALUES
(65, 653653653, NULL, NULL, 27),
(67, 653653652, 4, NULL, NULL),
(68, 654621687, NULL, 6, NULL),
(69, 655555555, NULL, NULL, 27);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `venta`
--

CREATE TABLE `venta` (
  `factura` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `Id_cliente` int(11) NOT NULL,
  `Id_personal` int(11) NOT NULL,
  `PrecioVenta` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `venta`
--

INSERT INTO `venta` (`factura`, `fecha`, `Id_cliente`, `Id_personal`, `PrecioVenta`) VALUES
(28, '2019-02-15', 27, 6, 610),
(29, '2019-12-31', 27, 6, 610),
(30, '2019-02-28', 27, 6, 900);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`ID_Cliente`);

--
-- Indices de la tabla `compra`
--
ALTER TABLE `compra`
  ADD PRIMARY KEY (`Factura`),
  ADD KEY `fk_proveedores_compras` (`id_proveedor`),
  ADD KEY `fk_personal_compras` (`id_personal`);

--
-- Indices de la tabla `escandallo`
--
ALTER TABLE `escandallo`
  ADD PRIMARY KEY (`ID_Escandallo`),
  ADD KEY `fk_escandallo_producto` (`ID_Producto`);

--
-- Indices de la tabla `escandallos_materiales`
--
ALTER TABLE `escandallos_materiales`
  ADD PRIMARY KEY (`ID_Escandallo_materiales`),
  ADD KEY `fk_escandallo_materiales` (`ID_Escandallo`),
  ADD KEY `fk_materiales_escandallo` (`ID_Material`);

--
-- Indices de la tabla `materiales`
--
ALTER TABLE `materiales`
  ADD PRIMARY KEY (`ID_Material`);

--
-- Indices de la tabla `ordenesfabrica`
--
ALTER TABLE `ordenesfabrica`
  ADD PRIMARY KEY (`ID_orden`),
  ADD KEY `fk_orden_escandallo` (`ID_Escandallo`),
  ADD KEY `fk_orden_personal` (`ID_Personal`);

--
-- Indices de la tabla `personal`
--
ALTER TABLE `personal`
  ADD PRIMARY KEY (`ID_Personal`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`ID_Producto`),
  ADD UNIQUE KEY `Nombre` (`Nombre`),
  ADD KEY `fk_productos_proveedores` (`ID_Proveedor`);

--
-- Indices de la tabla `productos_compra`
--
ALTER TABLE `productos_compra`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `fk_producto_compra` (`id_producto`),
  ADD KEY `fk_compra` (`Id_compra`);

--
-- Indices de la tabla `productos_ventas`
--
ALTER TABLE `productos_ventas`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `producto_venta` (`ID_Producto`),
  ADD KEY `ventaProducto` (`ID_Venta`);

--
-- Indices de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  ADD PRIMARY KEY (`ID_Proveedor`);

--
-- Indices de la tabla `proveedores_productos`
--
ALTER TABLE `proveedores_productos`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `fk_ProductosProveedores` (`ID_Poducto`),
  ADD KEY `fk_ProveedoresProductos` (`ID_Proveedor`);

--
-- Indices de la tabla `telefonos`
--
ALTER TABLE `telefonos`
  ADD PRIMARY KEY (`ID_Telefono`),
  ADD KEY `fk_telefonos_clientes` (`ID_Cliente`),
  ADD KEY `fk_telefonos_proveedores` (`ID_Proveedor`),
  ADD KEY `fk_telefonos_personal` (`ID_Personal`);

--
-- Indices de la tabla `venta`
--
ALTER TABLE `venta`
  ADD PRIMARY KEY (`factura`),
  ADD KEY `ventas_cli` (`Id_cliente`),
  ADD KEY `ventas_per` (`Id_personal`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `ID_Cliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT de la tabla `compra`
--
ALTER TABLE `compra`
  MODIFY `Factura` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT de la tabla `escandallo`
--
ALTER TABLE `escandallo`
  MODIFY `ID_Escandallo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `escandallos_materiales`
--
ALTER TABLE `escandallos_materiales`
  MODIFY `ID_Escandallo_materiales` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT de la tabla `materiales`
--
ALTER TABLE `materiales`
  MODIFY `ID_Material` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `ordenesfabrica`
--
ALTER TABLE `ordenesfabrica`
  MODIFY `ID_orden` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `personal`
--
ALTER TABLE `personal`
  MODIFY `ID_Personal` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `ID_Producto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT de la tabla `productos_compra`
--
ALTER TABLE `productos_compra`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- AUTO_INCREMENT de la tabla `productos_ventas`
--
ALTER TABLE `productos_ventas`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  MODIFY `ID_Proveedor` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `proveedores_productos`
--
ALTER TABLE `proveedores_productos`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `telefonos`
--
ALTER TABLE `telefonos`
  MODIFY `ID_Telefono` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=71;

--
-- AUTO_INCREMENT de la tabla `venta`
--
ALTER TABLE `venta`
  MODIFY `factura` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `compra`
--
ALTER TABLE `compra`
  ADD CONSTRAINT `fk_personal_compras` FOREIGN KEY (`id_personal`) REFERENCES `personal` (`ID_Personal`),
  ADD CONSTRAINT `fk_proveedores_compras` FOREIGN KEY (`id_proveedor`) REFERENCES `proveedores` (`ID_Proveedor`);

--
-- Filtros para la tabla `escandallo`
--
ALTER TABLE `escandallo`
  ADD CONSTRAINT `fk_escandallo_producto` FOREIGN KEY (`ID_Producto`) REFERENCES `productos` (`ID_Producto`);

--
-- Filtros para la tabla `escandallos_materiales`
--
ALTER TABLE `escandallos_materiales`
  ADD CONSTRAINT `fk_escandallo_materiales` FOREIGN KEY (`ID_Escandallo`) REFERENCES `escandallo` (`ID_Escandallo`),
  ADD CONSTRAINT `fk_materiales_escandallo` FOREIGN KEY (`ID_Material`) REFERENCES `productos` (`ID_Producto`);

--
-- Filtros para la tabla `ordenesfabrica`
--
ALTER TABLE `ordenesfabrica`
  ADD CONSTRAINT `fk_orden_escandallo` FOREIGN KEY (`ID_Escandallo`) REFERENCES `escandallo` (`ID_Escandallo`),
  ADD CONSTRAINT `fk_orden_personal` FOREIGN KEY (`ID_Personal`) REFERENCES `personal` (`ID_Personal`);

--
-- Filtros para la tabla `productos`
--
ALTER TABLE `productos`
  ADD CONSTRAINT `fk_productos_proveedores` FOREIGN KEY (`ID_Proveedor`) REFERENCES `proveedores` (`ID_Proveedor`);

--
-- Filtros para la tabla `productos_compra`
--
ALTER TABLE `productos_compra`
  ADD CONSTRAINT `fk_compra` FOREIGN KEY (`Id_compra`) REFERENCES `compra` (`Factura`),
  ADD CONSTRAINT `fk_producto_compra` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`ID_Producto`);

--
-- Filtros para la tabla `productos_ventas`
--
ALTER TABLE `productos_ventas`
  ADD CONSTRAINT `producto_venta` FOREIGN KEY (`ID_Producto`) REFERENCES `productos` (`ID_Producto`),
  ADD CONSTRAINT `ventaProducto` FOREIGN KEY (`ID_Venta`) REFERENCES `venta` (`factura`);

--
-- Filtros para la tabla `proveedores_productos`
--
ALTER TABLE `proveedores_productos`
  ADD CONSTRAINT `fk_ProductosProveedores` FOREIGN KEY (`ID_Poducto`) REFERENCES `productos` (`ID_Producto`),
  ADD CONSTRAINT `fk_ProveedoresProductos` FOREIGN KEY (`ID_Proveedor`) REFERENCES `proveedores` (`ID_Proveedor`);

--
-- Filtros para la tabla `telefonos`
--
ALTER TABLE `telefonos`
  ADD CONSTRAINT `fk_telefonos_clientes` FOREIGN KEY (`ID_Cliente`) REFERENCES `clientes` (`ID_Cliente`),
  ADD CONSTRAINT `fk_telefonos_personal` FOREIGN KEY (`ID_Personal`) REFERENCES `personal` (`ID_Personal`),
  ADD CONSTRAINT `fk_telefonos_proveedores` FOREIGN KEY (`ID_Proveedor`) REFERENCES `proveedores` (`ID_Proveedor`);

--
-- Filtros para la tabla `venta`
--
ALTER TABLE `venta`
  ADD CONSTRAINT `ventas_cli` FOREIGN KEY (`Id_cliente`) REFERENCES `clientes` (`ID_Cliente`),
  ADD CONSTRAINT `ventas_per` FOREIGN KEY (`Id_personal`) REFERENCES `personal` (`ID_Personal`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
