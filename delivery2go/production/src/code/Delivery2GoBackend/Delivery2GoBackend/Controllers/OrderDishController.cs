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
	public class OrderDishController : ApiController
	{

		deliveryContext context;
		QueryBuilder<OrderDish> queryBuider;

		public OrderDishController()
		{
			context = new deliveryContext();
			queryBuider = new QueryBuilder<OrderDish>()
				.MapMember("OrderCode", x => x.Order.Code)
				.MapMember("DishName", x => x.Dish.Name)
				.MapMember("DressingName", x => x.Dressing.Name)
				.MapMember("UpdateUserName", x => x.UpdateUser.Name);
		}

		// GET api/orderdish/get/
		public System.Collections.ICollection Get(string filter = null, string orderby = null, int top = -1, int skip = -1)
		{
			if (orderby == null)
			{
				orderby = "Id";
			}
			return queryBuider.CreateQuery(context.OrderDishs.AsNoTracking(), filter, orderby, skip, top, x => new
				{
					Id=x.Id,
					OrderId=x.OrderId,
					OrderCode=x.Order.Code,
					DishId=x.DishId,
					DishName=x.Dish.Name,
					DishPrice=x.DishPrice,
					Quantity=x.Quantity,
					SizeId=x.SizeId,
					DressingId=x.DressingId,
					DressingName=x.Dressing.Name,
					DressingPrice=x.DressingPrice,
					SubTotal=x.SubTotal,
					UpdateDate=x.UpdateDate,
					Remarks=x.Remarks,
					UpdateUserId=x.UpdateUserId,
					UpdateUserName=x.UpdateUser.Name,
				}).ToList();
		}

		// GET api/orderdish/get/5
		public object Get(int id)
		{
			return context.OrderDishs.AsNoTracking()
				.Where(x=>x.Id == id)
				.Select(x=> new
				{
					Id=x.Id,
					OrderId=x.OrderId,
					OrderCode=x.Order.Code,
					DishId=x.DishId,
					DishName=x.Dish.Name,
					DishPrice=x.DishPrice,
					Quantity=x.Quantity,
					SizeId=x.SizeId,
					DressingId=x.DressingId,
					DressingName=x.Dressing.Name,
					DressingPrice=x.DressingPrice,
					SubTotal=x.SubTotal,
					UpdateDate=x.UpdateDate,
					Remarks=x.Remarks,
					UpdateUserId=x.UpdateUserId,
					UpdateUserName=x.UpdateUser.Name,
				})
			.FirstOrDefault();
		}

		// POST api/orderdish/
		public HttpResponseMessage Post([FromBody]OrderDish value)
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

		// PUT api/orderdish/
		public HttpResponseMessage Put([FromBody]OrderDish value, string filter = null)
		{
			ServerValidationInfo vi = null;
			value.UpdateDate=DateTime.Now;
			if (!ModelState.IsValid)
			{
				vi = new ServerValidationInfo(ModelState);
			}
			if (filter == null)
			{
				context.Entry(value).State = System.Data.EntityState.Modified;
			}
			else
			{
				var old = context.OrderDishs.SingleOrDefault(queryBuider.CreateWhere(filter));
				old.Id = value.Id;
				old.OrderId = value.OrderId;
				old.DishId = value.DishId;
				old.DishPrice = value.DishPrice;
				old.Quantity = value.Quantity;
				old.SizeId = value.SizeId;
				old.DressingId = value.DressingId;
				old.DressingPrice = value.DressingPrice;
				old.SubTotal = value.SubTotal;
				old.UpdateDate = value.UpdateDate;
				old.Remarks = value.Remarks;
				old.UpdateUserId = value.UpdateUserId;
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			var result = context.SaveChanges() > 0;
			return Request.CreateResponse(HttpStatusCode.OK, result);
		}

		// DELETE api/orderdish/
		public bool Delete(string filter = null)
		{
			IQueryable<OrderDish> query = context.OrderDishs;
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			foreach (var item in query)
			{
				context.OrderDishs.Remove(item);
			}
			return context.SaveChanges() > 0;
		}

		// GET api/orderdish/count
		[HttpGet]
		public int Count(string filter = null)
		{
			IQueryable<OrderDish> query = context.OrderDishs.AsNoTracking();
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			return query.Count();
		}

		// GET api/orderdish/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.OrderDishs.AsNoTracking()
				.Where(queryBuider.CreateWhere(filter))
				.Select(x=> new
				{
					Id=x.Id,
					OrderId=x.OrderId,
					OrderCode=x.Order.Code,
					DishId=x.DishId,
					DishName=x.Dish.Name,
					DishPrice=x.DishPrice,
					Quantity=x.Quantity,
					SizeId=x.SizeId,
					DressingId=x.DressingId,
					DressingName=x.Dressing.Name,
					DressingPrice=x.DressingPrice,
					SubTotal=x.SubTotal,
					UpdateDate=x.UpdateDate,
					Remarks=x.Remarks,
					UpdateUserId=x.UpdateUserId,
					UpdateUserName=x.UpdateUser.Name,
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
