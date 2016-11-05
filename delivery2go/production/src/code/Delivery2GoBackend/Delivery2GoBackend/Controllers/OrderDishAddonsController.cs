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
	public class OrderDishAddonsController : ApiController
	{

		deliveryContext context;
		QueryBuilder<OrderDishAddons> queryBuider;

		public OrderDishAddonsController()
		{
			context = new deliveryContext();
			queryBuider = new QueryBuilder<OrderDishAddons>()
				.MapMember("OrderDishRemarks", x => x.OrderDish.Remarks)
				.MapMember("AddonName", x => x.Addon.Name)
				.MapMember("UpdateUserName", x => x.UpdateUser.Name);
		}

		// GET api/orderdishaddons/get/
		public System.Collections.ICollection Get(string filter = null, string orderby = null, int top = -1, int skip = -1)
		{
			if (orderby == null)
			{
				orderby = "OrderDishId,AddonId";
			}
			return queryBuider.CreateQuery(context.OrderDishAddonses.AsNoTracking(), filter, orderby, skip, top, x => new
				{
					OrderDishId=x.OrderDishId,
					OrderDishRemarks=x.OrderDish.Remarks,
					AddonId=x.AddonId,
					AddonName=x.Addon.Name,
					AddonPrice=x.AddonPrice,
					UpdateDate=x.UpdateDate,
					Remarks=x.Remarks,
					UpdateUserId=x.UpdateUserId,
					UpdateUserName=x.UpdateUser.Name,
				}).ToList();
		}

		// GET api/orderdishaddons/get/5
		public object Get(int id)
		{
			return context.OrderDishAddonses.AsNoTracking()
				.Where(x=>x.OrderDishId == id)
				.Select(x=> new
				{
					OrderDishId=x.OrderDishId,
					OrderDishRemarks=x.OrderDish.Remarks,
					AddonId=x.AddonId,
					AddonName=x.Addon.Name,
					AddonPrice=x.AddonPrice,
					UpdateDate=x.UpdateDate,
					Remarks=x.Remarks,
					UpdateUserId=x.UpdateUserId,
					UpdateUserName=x.UpdateUser.Name,
				})
			.FirstOrDefault();
		}

		// POST api/orderdishaddons/
		public HttpResponseMessage Post([FromBody]OrderDishAddons value)
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

		// PUT api/orderdishaddons/
		public HttpResponseMessage Put([FromBody]OrderDishAddons value, string filter = null)
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
				var old = context.OrderDishAddonses.SingleOrDefault(queryBuider.CreateWhere(filter));
				old.OrderDishId = value.OrderDishId;
				old.AddonId = value.AddonId;
				old.AddonPrice = value.AddonPrice;
				old.UpdateDate = value.UpdateDate;
				old.Remarks = value.Remarks;
				old.UpdateUserId = value.UpdateUserId;
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			var result = context.SaveChanges() > 0;
			return Request.CreateResponse(HttpStatusCode.OK, result);
		}

		// DELETE api/orderdishaddons/
		public bool Delete(string filter = null)
		{
			IQueryable<OrderDishAddons> query = context.OrderDishAddonses;
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			foreach (var item in query)
			{
				context.OrderDishAddonses.Remove(item);
			}
			return context.SaveChanges() > 0;
		}

		// GET api/orderdishaddons/count
		[HttpGet]
		public int Count(string filter = null)
		{
			IQueryable<OrderDishAddons> query = context.OrderDishAddonses.AsNoTracking();
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			return query.Count();
		}

		// GET api/orderdishaddons/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.OrderDishAddonses.AsNoTracking()
				.Where(queryBuider.CreateWhere(filter))
				.Select(x=> new
				{
					OrderDishId=x.OrderDishId,
					OrderDishRemarks=x.OrderDish.Remarks,
					AddonId=x.AddonId,
					AddonName=x.Addon.Name,
					AddonPrice=x.AddonPrice,
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
