--Compras Detalles

SELECT 

idcompra as compraid, 
idarticulo as productoid,
cantidad, 
(costofinalmoneda * cambiomoneda) as preciomc 

FROM compradetalle;
