--Ventas

SELECT 

id as ventaid, 
fecha as vfecha, 
totalgs as vmonto

FROM venta
WHERE iddeposito NOT IN (7,4,3,5);