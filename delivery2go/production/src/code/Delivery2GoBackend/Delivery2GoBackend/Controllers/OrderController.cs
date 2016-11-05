using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using Enterlib;
using Enterlib.Parsing;
using Enterlib.Validation;
using Delivery2GoBackend.Models;

namespace Delivery2GoBackend.Controllers
{
	public class OrderController : ApiController
	{

		deliveryContext context;
		QueryBuilder<Order> queryBuider;

		public OrderController()
		{
			context = new deliveryContext();
			queryBuider = new QueryBuilder<Order>()
				.MapMember("EntityName", x => x.Entity.Name)
				.MapMember("ClientName", x => x.Client.Name)
				.MapMember("OrderStateName", x => x.OrderState.Name)
				.MapMember("OrderTypeName", x => x.OrderType.Name)                
				.MapMember("ClientSignatureLocation", x => x.ClientSignature.Location)
				.MapMember("UpdateUserName", x => x.UpdateUser.Name);
		}

		// GET api/order/get/
		public System.Collections.ICollection Get(string filter = null, string orderby = null, int top = -1, int skip = -1)
		{
			if (orderby == null)
			{
				orderby = "OrderId";
			}
			return queryBuider.CreateQuery(context.Orders.AsNoTracking(), filter, orderby, skip, top, x => new
				{
					OrderId=x.OrderId,
					Code=x.Code,
					EntityId=x.EntityId,
					EntityName=x.Entity.Name,
					ClientId=x.ClientId,
					ClientName=x.Client.Name,
					CreateDate=x.CreateDate,
					TotalAmount=x.TotalAmount,
					DeliveryCharge=x.DeliveryCharge,
					TaxAmount=x.TaxAmount,
					UpdateDate=x.UpdateDate,
					DeliveryAdress=x.DeliveryAdress,
					Phone=x.Phone,
					DeliveryLat=x.DeliveryLat,
					DeliveryLng=x.DeliveryLng,
					OrderStateId=x.OrderStateId,
					OrderStateName=x.OrderState.Name,
					OrderTypeId=x.OrderTypeId,
					OrderTypeName=x.OrderType.Name,
					PaymentRef=x.PaymentRef,
					ClientSignatureId=x.ClientSignatureId,
					ClientSignatureLocation=x.ClientSignature.Location,
					DeliveryTimeMinutes=x.DeliveryTimeMinutes,
					Remarks=x.Remarks,
					UpdateUserId=x.UpdateUserId,
					UpdateUserName=x.UpdateUser.Name,
                    ClientNotified = x.ClientNotified,
                    EntityNotified = x.EntityNotified
				}).ToList();
		}

		// GET api/order/get/5
		public object Get(int id)
		{
			return context.Orders.AsNoTracking()
				.Where(x=>x.OrderId == id)
				.Select(x=> new
				{
					OrderId=x.OrderId,
					Code=x.Code,
					EntityId=x.EntityId,
					EntityName=x.Entity.Name,
					ClientId=x.ClientId,
					ClientName=x.Client.Name,
					CreateDate=x.CreateDate,
					TotalAmount=x.TotalAmount,
					DeliveryCharge=x.DeliveryCharge,
					TaxAmount=x.TaxAmount,
					UpdateDate=x.UpdateDate,
					DeliveryAdress=x.DeliveryAdress,
					Phone=x.Phone,
					DeliveryLat=x.DeliveryLat,
					DeliveryLng=x.DeliveryLng,
					OrderStateId=x.OrderStateId,
					OrderStateName=x.OrderState.Name,
					OrderTypeId=x.OrderTypeId,
					OrderTypeName=x.OrderType.Name,
					PaymentRef=x.PaymentRef,
					ClientSignatureId=x.ClientSignatureId,
					ClientSignatureLocation=x.ClientSignature.Location,
					DeliveryTimeMinutes=x.DeliveryTimeMinutes,
					Remarks=x.Remarks,
					UpdateUserId=x.UpdateUserId,
					UpdateUserName=x.UpdateUser.Name,
                    ClientNotified = x.ClientNotified,
                    EntityNotified = x.EntityNotified
				})
			.FirstOrDefault();
		}

		// POST api/order/
		public HttpResponseMessage Post([FromBody]Order value)
		{
			ServerValidationInfo vi = null;
			value.UpdateDate=DateTime.Now;
			if (!ModelState.IsValid)
			{
				vi = new ServerValidationInfo(ModelState);
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			context.Entry(value).State = System.Data.EntityState.Added;
			context.SaveChanges();

			return Request.CreateResponse(HttpStatusCode.OK, value);
		}

		// PUT api/order/
		public HttpResponseMessage Put([FromBody]Order value, string filter = null)
		{
			ServerValidationInfo vi = null;
			value.UpdateDate=DateTime.Now;

            bool sendNotification =false;

			if (!ModelState.IsValid)
			{
				vi = new ServerValidationInfo(ModelState);
			}
            if (filter == null)
            {
                var old = context.Orders.AsNoTracking().SingleOrDefault(x => x.OrderId == value.OrderId);
                sendNotification = old.OrderStateId == OrderStates.Open && value.OrderStateId == OrderStates.Submited;

                context.Entry(value).State = System.Data.EntityState.Modified;
            }
            else
            {
                var old = context.Orders.SingleOrDefault(queryBuider.CreateWhere(filter));
                sendNotification = old.OrderStateId == OrderStates.Open && value.OrderStateId == OrderStates.Submited;


                old.OrderId = value.OrderId;
                old.Code = value.Code;
                old.EntityId = value.EntityId;
                old.ClientId = value.ClientId;
                old.CreateDate = value.CreateDate;
                old.TotalAmount = value.TotalAmount;
                old.DeliveryCharge = value.DeliveryCharge;
                old.TaxAmount = value.TaxAmount;
                old.UpdateDate = value.UpdateDate;
                old.DeliveryAdress = value.DeliveryAdress;
                old.Phone = value.Phone;
                old.DeliveryLat = value.DeliveryLat;
                old.DeliveryLng = value.DeliveryLng;
                old.OrderStateId = value.OrderStateId;
                old.OrderTypeId = value.OrderTypeId;
                old.PaymentRef = value.PaymentRef;
                old.ClientSignatureId = value.ClientSignatureId;
                old.DeliveryTimeMinutes = value.DeliveryTimeMinutes;
                old.Remarks = value.Remarks;
                old.UpdateUserId = value.UpdateUserId;
                old.ClientNotified = value.ClientNotified;
                old.EntityNotified = value.EntityNotified;
            }
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);

			var result = context.SaveChanges() > 0;

            if (result && sendNotification)
            {
                NotificationService notificationSrv = new NotificationService(context);
                try
                {
                    notificationSrv.NotifyOrderSubmited(value);
                }
                catch
                {

                }
            }

			return Request.CreateResponse(HttpStatusCode.OK, result);
		}

		// DELETE api/order/
		public bool Delete(string filter = null)
		{
			IQueryable<Order> query = context.Orders;
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			foreach (var item in query)
			{
				context.Orders.Remove(item);
			}
			return context.SaveChanges() > 0;
		}

		// GET api/order/count
		[HttpGet]
		public int Count(string filter = null)
		{
			IQueryable<Order> query = context.Orders.AsNoTracking();
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			return query.Count();
		}

		// GET api/order/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.Orders.AsNoTracking()
				.Where(queryBuider.CreateWhere(filter))
				.Select(x=> new
				{
					OrderId=x.OrderId,
					Code=x.Code,
					EntityId=x.EntityId,
					EntityName=x.Entity.Name,
					ClientId=x.ClientId,
					ClientName=x.Client.Name,
					CreateDate=x.CreateDate,
					TotalAmount=x.TotalAmount,
					DeliveryCharge=x.DeliveryCharge,
					TaxAmount=x.TaxAmount,
					UpdateDate=x.UpdateDate,
					DeliveryAdress=x.DeliveryAdress,
					Phone=x.Phone,
					DeliveryLat=x.DeliveryLat,
					DeliveryLng=x.DeliveryLng,
					OrderStateId=x.OrderStateId,
					OrderStateName=x.OrderState.Name,
					OrderTypeId=x.OrderTypeId,
					OrderTypeName=x.OrderType.Name,
					PaymentRef=x.PaymentRef,
					ClientSignatureId=x.ClientSignatureId,
					ClientSignatureLocation=x.ClientSignature.Location,
					DeliveryTimeMinutes=x.DeliveryTimeMinutes,
					Remarks=x.Remarks,
					UpdateUserId=x.UpdateUserId,
					UpdateUserName=x.UpdateUser.Name,
                    ClientNotified = x.ClientNotified,
                    EntityNotified = x.EntityNotified
				})
			.FirstOrDefault();
		}

		protected override void Dispose(bool disposing)
		{
			if (disposing)
				context.Dispose();
			base.Dispose(disposing);
		}


	}
}
