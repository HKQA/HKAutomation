#!/bin/bash
echo "Reporting initialized.."

yesterday=`date -d 'yesterday' +%Y-%m-%d`
cd /home/release/reports

echo "deleting old reports.."
rm -f /home/release/reports/*.xls
echo "old reports deleted.."

echo "Wait, Generating fresh reports..!!"

mysql --host=192.168.70.28 --user=hkadmin --password=admin2K11! bright_prod -e "SELECT so.id B2C_BRT_SO_ID, li.id LI_ID, cli.id CLI_ID, cli.qty BO_QTY, li.qty SO_QTY, SUM(pvi.qty)*-1 PVI_QTY
    FROM shipment sh JOIN shipping_order so ON sh.shipping_order_id=so.id
      JOIN base_order bo ON so.base_order_id = bo.id AND bo.order_type_id =30
      JOIN line_item li ON li.shipping_order_id=so.id
      JOIN cart_line_item cli ON li.cart_line_item_id = cli.id
      JOIN product_variant_inventory pvi ON li.shipping_order_id = pvi.shipping_order_id
        AND li.id = pvi.line_item_id AND pvi.inv_txn_type_id = 20
    WHERE DATE(sh.ship_date) = '$yesterday'
GROUP BY 1,2,3,4,5;" >> /home/release/reports/B2C_BO_SO_QTY.xls

mysql --host=192.168.70.28 --user=hkadmin --password=admin2K11! bright_prod -e "SELECT so.id B2C_BRT_SO_ID, li.id LI_ID, cli.id CLI_ID, cli.qty BO_QTY, li.qty SO_QTY, SUM(pvi.qty)*-1 PVI_QTY
    FROM shipment sh JOIN shipping_order so ON sh.shipping_order_id=so.id
      JOIN base_order bo ON so.base_order_id = bo.id AND bo.order_type_id =30
      JOIN line_item li ON li.shipping_order_id=so.id
      JOIN cart_line_item cli ON li.cart_line_item_id = cli.id
      JOIN product_variant_inventory pvi ON li.shipping_order_id = pvi.shipping_order_id
        AND li.id = pvi.line_item_id AND pvi.inv_txn_type_id = 20
    WHERE DATE(sh.ship_date) = '$yesterday'
GROUP BY 1,2,3,4,5
having BO_QTY <> SO_QTY;" >> /home/release/reports/B2C_BO_SO_QTY_mismatch.xls


mysql --host=192.168.70.28 --user=hkadmin --password=admin2K11! bright_prod -e "SELECT so.id B2C_BRT_SO_ID, li.id LI_ID, cli.id CLI_ID, cli.qty BO_QTY, li.qty SO_QTY, SUM(pvi.qty)*-1 PVI_QTY
    FROM shipment sh JOIN shipping_order so ON sh.shipping_order_id=so.id
      JOIN base_order bo ON so.base_order_id = bo.id AND bo.order_type_id =30
      JOIN line_item li ON li.shipping_order_id=so.id
      JOIN cart_line_item cli ON li.cart_line_item_id = cli.id
      JOIN product_variant_inventory pvi ON li.shipping_order_id = pvi.shipping_order_id
        AND li.id = pvi.line_item_id AND pvi.inv_txn_type_id = 20
    WHERE DATE(sh.ship_date) = '$yesterday'
GROUP BY 1,2,3,4,5
having BO_QTY <> PVI_QTY;" >> /home/release/reports/B2C_BO_SO_ChkOut_QTY_mismatch.xls

echo "B2C reports generated..!!"

# B2B report:
mysql --host=192.168.70.28 --user=hkadmin --password=admin2K11! bright_prod -e "SELECT so.id B2B_BRT_SO_ID, li.id LI_ID, cli.id CLI_ID, cli.qty BO_QTY, li.qty SO_QTY, SUM(pvi.qty)*-1 PVI_QTY
   FROM shipment sh JOIN shipping_order so ON sh.shipping_order_id=so.id
     JOIN base_order bo ON so.base_order_id = bo.id AND bo.order_type_id IN (10,11,13,14)
      JOIN line_item li ON li.shipping_order_id=so.id
      JOIN cart_line_item cli ON li.cart_line_item_id = cli.id
      JOIN product_variant_inventory pvi ON li.shipping_order_id = pvi.shipping_order_id
        AND li.id = pvi.line_item_id AND pvi.inv_txn_type_id = 20
      WHERE DATE(sh.ship_date) = '$yesterday'
GROUP BY 1,2,3,4,5;" >> /home/release/reports/B2B_BO_SO_QTY.xls

mysql --host=192.168.70.28 --user=hkadmin --password=admin2K11! bright_prod -e "SELECT so.id B2B_BRT_SO_ID, li.id LI_ID, cli.id CLI_ID, cli.qty BO_QTY, li.qty SO_QTY, SUM(pvi.qty)*-1 PVI_QTY
   FROM shipment sh JOIN shipping_order so ON sh.shipping_order_id=so.id
     JOIN base_order bo ON so.base_order_id = bo.id AND bo.order_type_id IN (10,11,13,14)
      JOIN line_item li ON li.shipping_order_id=so.id
      JOIN cart_line_item cli ON li.cart_line_item_id = cli.id
      JOIN product_variant_inventory pvi ON li.shipping_order_id = pvi.shipping_order_id
        AND li.id = pvi.line_item_id AND pvi.inv_txn_type_id = 20
      WHERE DATE(sh.ship_date) = '$yesterday'
GROUP BY 1,2,3,4,5
having BO_QTY <> SO_QTY;" >> /home/release/reports/B2B_BO_SO_QTY_mismatch.xls

mysql --host=192.168.70.28 --user=hkadmin --password=admin2K11! bright_prod -e "SELECT so.id B2B_BRT_SO_ID, li.id LI_ID, cli.id CLI_ID, cli.qty BO_QTY, li.qty SO_QTY, SUM(pvi.qty)*-1 PVI_QTY
   FROM shipment sh JOIN shipping_order so ON sh.shipping_order_id=so.id
     JOIN base_order bo ON so.base_order_id = bo.id AND bo.order_type_id IN (10,11,13,14)
      JOIN line_item li ON li.shipping_order_id=so.id
      JOIN cart_line_item cli ON li.cart_line_item_id = cli.id
      JOIN product_variant_inventory pvi ON li.shipping_order_id = pvi.shipping_order_id
        AND li.id = pvi.line_item_id AND pvi.inv_txn_type_id = 20
      WHERE DATE(sh.ship_date) = '$yesterday'
GROUP BY 1,2,3,4,5
having BO_QTY <> PVI_QTY;" >> /home/release/reports/B2B_BO_SO_ChkOut_QTY_mismatch.xls

echo "B2B reports generated..!!"

# B2B_CFA report:
mysql --host=192.168.70.28 --user=hkadmin --password=admin2K11! bright_prod -e "SELECT so.id B2B_BRT_SO_ID, li.id LI_ID, cli.id CLI_ID, cli.qty BO_QTY, li.qty SO_QTY, SUM(pvi.qty)*-1 PVI_QTY
   FROM shipment sh JOIN shipping_order so ON sh.shipping_order_id=so.id
     JOIN base_order bo ON so.base_order_id = bo.id AND bo.order_type_id = 12
      JOIN line_item li ON li.shipping_order_id=so.id
      JOIN cart_line_item cli ON li.cart_line_item_id = cli.id
      JOIN product_variant_inventory pvi ON li.shipping_order_id = pvi.shipping_order_id
        AND li.id = pvi.line_item_id AND pvi.inv_txn_type_id = 20
      WHERE DATE(sh.ship_date) = '$yesterday'
GROUP BY 1,2,3,4,5;" >> /home/release/reports/B2B_CFA_BO_SO_QTY.xls

mysql --host=192.168.70.28 --user=hkadmin --password=admin2K11! bright_prod -e "SELECT so.id B2B_BRT_SO_ID, li.id LI_ID, cli.id CLI_ID, cli.qty BO_QTY, li.qty SO_QTY, SUM(pvi.qty)*-1 PVI_QTY
   FROM shipment sh JOIN shipping_order so ON sh.shipping_order_id=so.id
     JOIN base_order bo ON so.base_order_id = bo.id AND bo.order_type_id = 12
      JOIN line_item li ON li.shipping_order_id=so.id
      JOIN cart_line_item cli ON li.cart_line_item_id = cli.id
      JOIN product_variant_inventory pvi ON li.shipping_order_id = pvi.shipping_order_id
        AND li.id = pvi.line_item_id AND pvi.inv_txn_type_id = 20
      WHERE DATE(sh.ship_date) = '$yesterday'
GROUP BY 1,2,3,4,5
having BO_QTY <> SO_QTY;" >> /home/release/reports/B2B_CFA_BO_SO_QTY_mismatch.xls

mysql --host=192.168.70.28 --user=hkadmin --password=admin2K11! bright_prod -e "SELECT so.id B2B_BRT_SO_ID, li.id LI_ID, cli.id CLI_ID, cli.qty BO_QTY, li.qty SO_QTY, SUM(pvi.qty)*-1 PVI_QTY
   FROM shipment sh JOIN shipping_order so ON sh.shipping_order_id=so.id
     JOIN base_order bo ON so.base_order_id = bo.id AND bo.order_type_id = 12
      JOIN line_item li ON li.shipping_order_id=so.id
      JOIN cart_line_item cli ON li.cart_line_item_id = cli.id
      JOIN product_variant_inventory pvi ON li.shipping_order_id = pvi.shipping_order_id
        AND li.id = pvi.line_item_id AND pvi.inv_txn_type_id = 20
      WHERE DATE(sh.ship_date) = '$yesterday'
GROUP BY 1,2,3,4,5
having BO_QTY <> PVI_QTY;" >> /home/release/reports/B2B_CFA_BO_SO_ChkOut_QTY_mismatch.xls

echo "All reports generated..!!"

attachment1="/home/release/reports/B2C_BO_SO_QTY.xls" 
attachment2="/home/release/reports/B2C_BO_SO_QTY_mismatch.xls"
attachment3="/home/release/reports/B2C_BO_SO_ChkOut_QTY_mismatch.xls "
attachment4="/home/release/reports/B2B_BO_SO_QTY.xls"
attachment5="/home/release/reports/B2B_BO_SO_QTY_mismatch.xls"
attachment6="/home/release/reports/B2B_BO_SO_ChkOut_QTY_mismatch.xls"
attachment7="/home/release/reports/B2B_CFA_BO_SO_QTY.xls" 
attachment8="/home/release/reports/B2B_CFA_BO_SO_QTY_mismatch.xls"
attachment9="/home/release/reports/B2B_CFA_BO_SO_ChkOut_QTY_mismatch.xls"

email="hulk@brightlifecare.com"

echo -e "Mailing the Reports to $email"
cat /home/release/reports/discription.txt | mail -s "Shipped orders' Booking/Checkout quantity reports on BRIGHT " -a $attachment1 -a $attachment2 -a $attachment3 -a $attachment4 -a $attachment5 -a $attachment6 -a $attachment7 -a $attachment8 -a $attachment9 $email
