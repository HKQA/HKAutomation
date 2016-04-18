#!/bin/bash

echo "reporting initialized..!!"

yesterday=`date -d 'yesterday' +%Y-%m-%d`
cd /home/release/reports

echo "deleting old reports.."
rm -f /home/release/reports/*Multiple_*.xls
echo "old reports deleted.."

echo "Wait, Generating fresh reports..!!"


mysql --host=192.168.70.28 --user=hkadmin --password=admin2K11! hk_qa -e "SELECT shipping_order_id,COUNT(*) cnt,MIN(activity_date),MAX(activity_date),
MIN(activity_date)-MAX(activity_date)
FROM shipping_order_lifecycle WHERE
shipping_order_lifecycle_activity_id=655
AND DATE(activity_date)='$yesterday'
GROUP BY 1
HAVING COUNT(*)>1
AND MIN(activity_date)-MAX(activity_date)<-120;" >> /home/release/reports/Multiple_Ship_orders.xls

echo "All reports generated..!!"

attachment1="/home/release/reports/Multiple_Ship_orders.xls"

email="hulk@brightlifecare.com"

echo -e "Mailing the Reports to $email"
cat /home/release/reports/discription2.txt | mail -s "Multiple times Shipped orders report on AQUA " -a $attachment1 $email

