--Ventas Detalles

SELECT 

vd.idventa as ventaid ,  
vd.idarticulo as productoid,  
vd.cantidad,  
vd.preciounitariogs as preciomv 

FROM ventadetalle vd 
JOIN venta v ON v.id = vd.idventa 
WHERE v.iddeposito NOT IN (7,4,3,5);