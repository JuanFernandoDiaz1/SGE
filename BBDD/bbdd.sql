-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 15-01-2021 a las 19:43:25
-- Versión del servidor: 10.4.11-MariaDB
-- Versión de PHP: 7.4.6

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
(13, '2020-12-11', 6, 4, 150),
(14, '2020-12-21', 6, 4, 15);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `escandallo`
--

CREATE TABLE `escandallo` (
  `ID_Escandallo` int(11) NOT NULL,
  `ID_Producto` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
  `ID_Proveedor` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`Nombre`, `Descripcion`, `Precio`, `PrecioVenta`, `Stock`, `ID_Producto`, `ID_Proveedor`) VALUES
('Raton', 'raton gaming', 15, 20, -5, 18, 4);

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
(33, 18, 13, 10),
(35, 18, 14, 1);

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
(24, 18, 22, 30),
(25, 18, 23, 31),
(26, 18, 24, 5);

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
('Agustin', '012012012X', 'Calle 1', 'jordan@gmail.com', 4);

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
(22, '2020-12-20', 27, 6, 600),
(23, '2020-12-21', 27, 6, 620),
(24, '2020-12-21', 27, 6, 100);

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
  MODIFY `Factura` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `escandallo`
--
ALTER TABLE `escandallo`
  MODIFY `ID_Escandallo` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `escandallos_materiales`
--
ALTER TABLE `escandallos_materiales`
  MODIFY `ID_Escandallo_materiales` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `materiales`
--
ALTER TABLE `materiales`
  MODIFY `ID_Material` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `ordenesfabrica`
--
ALTER TABLE `ordenesfabrica`
  MODIFY `ID_orden` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `personal`
--
ALTER TABLE `personal`
  MODIFY `ID_Personal` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `ID_Producto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT de la tabla `productos_compra`
--
ALTER TABLE `productos_compra`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT de la tabla `productos_ventas`
--
ALTER TABLE `productos_ventas`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  MODIFY `ID_Proveedor` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

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
  MODIFY `factura` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

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
  ADD CONSTRAINT `fk_materiales_escandallo` FOREIGN KEY (`ID_Material`) REFERENCES `materiales` (`ID_Material`);

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
