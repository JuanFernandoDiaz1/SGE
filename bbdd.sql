-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 20-11-2020 a las 21:01:56
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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `compras`
--

CREATE TABLE `compras` (
  `ID_Compras` int(11) NOT NULL,
  `Fecha` date NOT NULL,
  `Factura` int(11) NOT NULL,
  `ID_Personal` int(11) NOT NULL,
  `ID_Proveedor` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `escandallo`
--

CREATE TABLE `escandallo` (
  `ID_Escandallo` int(11) NOT NULL,
  `ID_Personal` int(11) NOT NULL,
  `ID_Producto` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ordenes-productos`
--

CREATE TABLE `ordenes-productos` (
  `ID` int(11) NOT NULL,
  `ID_Poducto` int(11) NOT NULL,
  `ID_Ordenes` int(11) NOT NULL,
  `Unidades` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ordenesfabrica`
--

CREATE TABLE `ordenesfabrica` (
  `Unidades` int(11) NOT NULL,
  `ID_Escandallo` int(11) NOT NULL,
  `ID_orden` int(11) NOT NULL
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
('Rafael', '01111111Z', 1, 'Calle 1', 'rafa@gmail.com'),
('Mario', '02222222A', 2, 'Calle 2', 'mario@gmail.com'),
('Juanfer', '03333333X', 3, 'Calle 3', 'juanfer@gmail.com');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `Nombre` varchar(100) NOT NULL,
  `Descripcion` varchar(500) NOT NULL,
  `Precio` int(11) NOT NULL,
  `Stock` int(11) NOT NULL,
  `ID_Producto` int(11) NOT NULL,
  `ID_Proveedor` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos_ventas`
--

CREATE TABLE `productos_ventas` (
  `ID` int(11) NOT NULL,
  `ID_Poducto` int(11) NOT NULL,
  `ID_Venta` int(11) NOT NULL,
  `Unidades` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
('Paco', '1234567891235', 'Calle 1', 'paco@sanz.com', 1),
('Agustin', '4321987654325', 'Calle 2', 'agustin@gmail.com', 2);

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
(33, 987987987, 1, NULL, NULL),
(34, 999999999, 2, NULL, NULL),
(35, 632654987, NULL, 1, NULL),
(37, 653265152, NULL, 2, NULL),
(38, 989855223, NULL, 3, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

CREATE TABLE `ventas` (
  `ID_Ventas` int(11) NOT NULL,
  `Fecha` date NOT NULL,
  `Factura` int(11) NOT NULL,
  `ID_Cliente` int(11) NOT NULL,
  `ID_Personal` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`ID_Cliente`);

--
-- Indices de la tabla `compras`
--
ALTER TABLE `compras`
  ADD PRIMARY KEY (`ID_Compras`),
  ADD KEY `fk_compras_personal` (`ID_Personal`),
  ADD KEY `fk_compras_proveedor` (`ID_Proveedor`);

--
-- Indices de la tabla `escandallo`
--
ALTER TABLE `escandallo`
  ADD PRIMARY KEY (`ID_Escandallo`),
  ADD KEY `fk_escandallo_personal` (`ID_Personal`),
  ADD KEY `fk_escandallo_producto` (`ID_Producto`);

--
-- Indices de la tabla `ordenes-productos`
--
ALTER TABLE `ordenes-productos`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `fk_orden_producto` (`ID_Ordenes`),
  ADD KEY `fk_producto_orden` (`ID_Poducto`);

--
-- Indices de la tabla `ordenesfabrica`
--
ALTER TABLE `ordenesfabrica`
  ADD PRIMARY KEY (`ID_orden`),
  ADD KEY `fk_orden_escandallo` (`ID_Escandallo`);

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
  ADD KEY `fk_productos_proveedores` (`ID_Proveedor`);

--
-- Indices de la tabla `productos_ventas`
--
ALTER TABLE `productos_ventas`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `fk_ProductoVenta` (`ID_Poducto`),
  ADD KEY `fk_VentaProducto` (`ID_Venta`);

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
-- Indices de la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD PRIMARY KEY (`ID_Ventas`),
  ADD KEY `fk_ventas_clientes` (`ID_Cliente`),
  ADD KEY `fk_ventas_personal` (`ID_Personal`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `ID_Cliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT de la tabla `compras`
--
ALTER TABLE `compras`
  MODIFY `ID_Compras` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `escandallo`
--
ALTER TABLE `escandallo`
  MODIFY `ID_Escandallo` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `ordenes-productos`
--
ALTER TABLE `ordenes-productos`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `ordenesfabrica`
--
ALTER TABLE `ordenesfabrica`
  MODIFY `ID_orden` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `personal`
--
ALTER TABLE `personal`
  MODIFY `ID_Personal` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `ID_Producto` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  MODIFY `ID_Proveedor` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `proveedores_productos`
--
ALTER TABLE `proveedores_productos`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `telefonos`
--
ALTER TABLE `telefonos`
  MODIFY `ID_Telefono` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;

--
-- AUTO_INCREMENT de la tabla `ventas`
--
ALTER TABLE `ventas`
  MODIFY `ID_Ventas` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `compras`
--
ALTER TABLE `compras`
  ADD CONSTRAINT `fk_compras_personal` FOREIGN KEY (`ID_Personal`) REFERENCES `personal` (`ID_Personal`),
  ADD CONSTRAINT `fk_compras_proveedor` FOREIGN KEY (`ID_Proveedor`) REFERENCES `proveedores` (`ID_Proveedor`);

--
-- Filtros para la tabla `escandallo`
--
ALTER TABLE `escandallo`
  ADD CONSTRAINT `fk_escandallo_personal` FOREIGN KEY (`ID_Personal`) REFERENCES `personal` (`ID_Personal`),
  ADD CONSTRAINT `fk_escandallo_producto` FOREIGN KEY (`ID_Producto`) REFERENCES `productos` (`ID_Producto`);

--
-- Filtros para la tabla `ordenes-productos`
--
ALTER TABLE `ordenes-productos`
  ADD CONSTRAINT `fk_orden_producto` FOREIGN KEY (`ID_Ordenes`) REFERENCES `ordenesfabrica` (`ID_orden`),
  ADD CONSTRAINT `fk_producto_orden` FOREIGN KEY (`ID_Poducto`) REFERENCES `productos` (`ID_Producto`);

--
-- Filtros para la tabla `ordenesfabrica`
--
ALTER TABLE `ordenesfabrica`
  ADD CONSTRAINT `fk_orden_escandallo` FOREIGN KEY (`ID_Escandallo`) REFERENCES `escandallo` (`ID_Escandallo`);

--
-- Filtros para la tabla `productos`
--
ALTER TABLE `productos`
  ADD CONSTRAINT `fk_productos_proveedores` FOREIGN KEY (`ID_Proveedor`) REFERENCES `proveedores` (`ID_Proveedor`);

--
-- Filtros para la tabla `productos_ventas`
--
ALTER TABLE `productos_ventas`
  ADD CONSTRAINT `fk_ProductoVenta` FOREIGN KEY (`ID_Poducto`) REFERENCES `productos` (`ID_Producto`),
  ADD CONSTRAINT `fk_VentaProducto` FOREIGN KEY (`ID_Venta`) REFERENCES `ventas` (`ID_Ventas`);

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
-- Filtros para la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD CONSTRAINT `fk_ventas_clientes` FOREIGN KEY (`ID_Cliente`) REFERENCES `clientes` (`ID_Cliente`),
  ADD CONSTRAINT `fk_ventas_personal` FOREIGN KEY (`ID_Personal`) REFERENCES `personal` (`ID_Personal`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
