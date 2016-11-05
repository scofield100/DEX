using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Net.Mail;
using System.Text;
using System.Web;
using Delivery2GoBackend.Models;
using System.Data.Entity.Infrastructure;
using System.Data.Entity;

namespace Delivery2GoBackend
{
    public class NotificationService
    {
        deliveryContext context;

        public NotificationService(deliveryContext context)
        {
            this.context = context;
        }

        public NotificationService()
            :this(new deliveryContext())
        {

        }

        public void NotifyOrderSubmited(Order order)
        {
            string host = ConfigurationManager.AppSettings["smtp_host"];
            string username = ConfigurationManager.AppSettings["smtp_username"];
            string password = ConfigurationManager.AppSettings["smtp_password"];
            string toAddr = ConfigurationManager.AppSettings["smtp_to"];
            string fromAddr = ConfigurationManager.AppSettings["smtp_from"];

            if (string.IsNullOrWhiteSpace(host)
                || string.IsNullOrWhiteSpace(username)
                || string.IsNullOrWhiteSpace(password)
                || string.IsNullOrWhiteSpace(toAddr)
                || string.IsNullOrWhiteSpace(fromAddr))
                throw new InvalidOperationException("La configuración smtp del servidor no está establecida");

            var smtpClient = new SmtpClient(host);
            smtpClient.Credentials = new System.Net.NetworkCredential(username, password);

            var mail = new MailMessage("\"Delivery Express\"" + fromAddr, toAddr,
                                             string.Format("New Order : {0} - {1}", order.OrderId, DateTime.Now.ToString("dd/MM/yyyy HH:mm")), 
                                             GetStringReport(order.OrderId));

            
            smtpClient.Send(mail);                            
        }

        private string GetStringReport(int orderId)
        {
            Order order = context.Orders
                .AsNoTracking()
                .Include(x => x.Client)
                .Include(x => x.Entity)                
                .FirstOrDefault(x => x.OrderId == orderId);

            var deliveryAgent = context.DeliveryAgents.Find(1);
            var restaurantPayment = order.TotalAmount - (order.TotalAmount * (deliveryAgent.DeliveryDiscount / 100.0));

            StringBuilder sb = new StringBuilder();
            sb.AppendLine("DELIVERY EXPRESS ORDER REPORT");
            sb.AppendLine();
            sb.AppendLine("ORDER DETAILS");
            sb.AppendLine("-----------------------");
            sb.AppendFormat("Id   : {0}", order.OrderId).AppendLine();
            sb.AppendFormat("Code   : {0}", order.Code).AppendLine();
            sb.AppendFormat("Restaurant : {0}", order.Entity.Name).AppendLine();
            sb.AppendFormat("Restaurant Address: {0}", order.Entity.Adress ?? "").AppendLine();
            sb.AppendLine();

            sb.AppendLine();
            sb.AppendLine("CLIENT DETAILS");
            sb.AppendLine("-----------------------");
            sb.AppendFormat("Id   : {0}", order.ClientId).AppendLine();
            sb.AppendFormat("Name   : {0} {1} {2}", order.Client.Name ?? "", order.Client.LastName ?? "", order.Client.LastName2 ?? "").AppendLine();
            sb.AppendFormat("Phone : {0}", order.Client.Phone ?? "").AppendLine();
            sb.AppendFormat("Mobile: {0}", order.Client.Mobile ?? "").AppendLine();
            sb.AppendFormat("Address No: {0}", order.Client.AdressNo ?? "").AppendLine();
            sb.AppendFormat("Address Street: {0}", order.Client.AddressStreet ?? "").AppendLine();
            sb.AppendLine();

            sb.AppendLine();
            sb.AppendLine("INVOICE DETAILS");
            sb.AppendLine("-----------------------");
            sb.AppendFormat("SubTotal   : {0:C}", order.TotalAmount).AppendLine();
            sb.AppendFormat("Tax   : {0:C}", order.TaxAmount).AppendLine();
            sb.AppendFormat("Delivery : {0:C}", order.DeliveryCharge).AppendLine();
            sb.AppendFormat("Total: {0:C}", order.TotalAmount + order.DeliveryCharge + order.TaxAmount).AppendLine();
            sb.AppendFormat("Restaurant Payment: {0:C}", restaurantPayment).AppendLine("\r\n");

            

            sb.AppendLine();
            sb.AppendLine("ITEMS DETAILS");            
            sb.AppendLine("************************");

            int count = 0;
            foreach (var item in context.OrderDishs.AsNoTracking()
                .Include(x=>x.Dish)
                .Include(x=>x.Dressing)
                .Include(x => x.Size)
                .Include(x => x.Size.Size)
                .Where(x => x.OrderId == orderId).ToList())
            {
                if (count > 0)
                {
                    sb.AppendLine("-----------------------");
                }

                sb.Append(' ', 1).AppendFormat(item.Dish.Name).AppendLine();
                if (item.SizeId != null)
                {
                    sb.Append(' ', 1).AppendFormat("Size  : {0} {1:C}", item.Size.Size.Name, item.Size.ExtraPrice).AppendLine();
                }
                sb.Append(' ', 1).AppendFormat("Dish Price : {0:C}", item.DishPrice).AppendLine();
                sb.Append(' ', 1).AppendFormat("Quantity : {0}", item.Quantity).AppendLine();
                sb.Append(' ', 1).AppendFormat("Subtotal: {0:C}", item.SubTotal??0).AppendLine();

                if (item.DressingId != null)
                {
                    sb.Append(' ', 1).AppendFormat("Dressing: {0} {1:C}", item.Dressing.Name, item.DressingPrice ?? 0).AppendLine();
                }
                
                var extras = context.OrderDishAddonses.AsNoTracking()
                    .Include(x => x.Addon)
                    .Where(x => x.OrderDishId == item.Id).ToList();

                if (extras.Count > 0)
                {
                    sb.AppendLine();
                    sb.Append(' ', 1).AppendLine("Extras:");
                    foreach (var extra in extras)
                    {
                        sb.Append(' ', 2).AppendFormat("{0} {1:C}", extra.Addon.Name, extra.AddonPrice).AppendLine();
                    }
                }

                count++;
            }            

            return sb.ToString();

        }
    }
}