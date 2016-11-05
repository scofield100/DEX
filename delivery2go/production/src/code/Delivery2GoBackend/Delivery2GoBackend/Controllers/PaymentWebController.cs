using Delivery2GoBackend.Models;
using Square.Connect.Api;
using Square.Connect.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace Delivery2GoBackend.Controllers
{
    public class PaymentWebController : Controller
    {
        private static TransactionApi _transactionApi;

        // The access token to use in all Connect API requests. 
        // Use your *sandbox* accesstoken if you're just testing things out.
        private static string _accessToken = "sandbox-sq0atb-I_0JktcytufuEwfZ60O2gQ";

        // The ID of the business location to associate processed payments with.        
        // See [Retrieve your business's locations]
        // (https://docs.connect.squareup.com/articles/getting-started/#retrievemerchantprofile)
        // for an easy way to get your business's location IDs.
        // If you're testing things out, use a sandbox location ID.
        private static string _locationId = "CBASEMbLRTCQYq4L-gsxjBCdOwY";
        private deliveryContext context;

        public PaymentWebController()
        {
            context = new deliveryContext();
            _transactionApi = new TransactionApi();
        }

        //
        // GET: /PaymentWeb/

        public ActionResult Index(int orderId)
        {
            ViewBag.OrderId = orderId;
            return View();
        }

        [HttpPost]
        public JsonResult Charge(string nonce, int orderId)
        {
            // Every payment you process with the SDK must have a unique idempotency key.
            // If you're unsure whether a particular payment succeeded, you can reattempt
            // it with the same idempotency key without worrying about double charging
            // the buyer.

            Order order = context.Orders.Find(orderId);
            string uuid;
            if (order == null)
            {
                return Json(null);
            }
            else
            {
                uuid = string.IsNullOrWhiteSpace(order.PaymentRef) ? 
                    NewIdempotencyKey() :
                    order.PaymentRef;             
            }
           
            double totalPayment = order.TotalAmount + order.TaxAmount + order.DeliveryCharge;
            totalPayment *= 100;
            // Monetary amounts are specified in the smallest unit of the applicable currency.
            // This amount is in cents. It's also hard-coded for $1.00, 
            // which isn't very useful.
            Money amount = NewMoney((int)totalPayment, "USD");

            ChargeRequest body = new ChargeRequest(AmountMoney: amount, IdempotencyKey: uuid, CardNonce: nonce);
            var response = _transactionApi.Charge(_accessToken, _locationId, body);

            if(response.Errors == null)
            {
                order.PaymentRef = uuid;
                context.SaveChanges();
                return Json(uuid);
            }

            return Json(null);
        }

        public static Money NewMoney(int amount, string currency)
        {
            return new Money(amount, Money.ToCurrencyEnum(currency));
        }

        public static string NewIdempotencyKey()
        {
            return Guid.NewGuid().ToString();
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
                context.Dispose();
            base.Dispose(disposing);
        }


    }
}
