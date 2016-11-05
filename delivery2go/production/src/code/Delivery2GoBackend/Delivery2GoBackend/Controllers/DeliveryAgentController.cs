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
	public class DeliveryAgentController : ApiController
	{

		deliveryContext context;
		QueryBuilder<DeliveryAgent> queryBuider;

		public DeliveryAgentController()
		{
			context = new deliveryContext();
			queryBuider = new QueryBuilder<DeliveryAgent>()
				.MapMember("UpdateUserName", x => x.UpdateUser.Name);
		}

		// GET api/deliveryagent/get/
		public System.Collections.ICollection Get(string filter = null, string orderby = null, int top = -1, int skip = -1)
		{
			if (orderby == null)
			{
				orderby = "Id";
			}
			return queryBuider.CreateQuery(context.DeliveryAgents.AsNoTracking(), filter, orderby, skip, top, x => new
				{
					Id=x.Id,
					Name=x.Name,
					Account=x.Account,
					DeliveryDiscount=x.DeliveryDiscount,
					UpdateDate=x.UpdateDate,
					CreateDate=x.CreateDate,
					UpdateUserId=x.UpdateUserId,
					UpdateUserName=x.UpdateUser.Name,
				}).ToList();
		}

		// GET api/deliveryagent/get/5
		public object Get(int id)
		{
			return context.DeliveryAgents.AsNoTracking()
				.Where(x=>x.Id == id)
				.Select(x=> new
				{
					Id=x.Id,
					Name=x.Name,
					Account=x.Account,
					DeliveryDiscount=x.DeliveryDiscount,
					UpdateDate=x.UpdateDate,
					CreateDate=x.CreateDate,
					UpdateUserId=x.UpdateUserId,
					UpdateUserName=x.UpdateUser.Name,
				})
			.FirstOrDefault();
		}

		// POST api/deliveryagent/
		public HttpResponseMessage Post([FromBody]DeliveryAgent value)
		{
			ServerValidationInfo vi = null;
			value.UpdateDate=DateTime.Now;
			value.CreateDate=DateTime.Now;
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

		// PUT api/deliveryagent/
		public HttpResponseMessage Put([FromBody]DeliveryAgent value, string filter = null)
		{
			ServerValidationInfo vi = null;
			value.UpdateDate=DateTime.Now;
			value.CreateDate=DateTime.Now;
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
				var old = context.DeliveryAgents.SingleOrDefault(queryBuider.CreateWhere(filter));
				old.Id = value.Id;
				old.Name = value.Name;
				old.Account = value.Account;
				old.DeliveryDiscount = value.DeliveryDiscount;
				old.UpdateDate = value.UpdateDate;
				old.CreateDate = value.CreateDate;
				old.UpdateUserId = value.UpdateUserId;
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			var result = context.SaveChanges() > 0;
			return Request.CreateResponse(HttpStatusCode.OK, result);
		}

		// DELETE api/deliveryagent/
		public bool Delete(string filter = null)
		{
			IQueryable<DeliveryAgent> query = context.DeliveryAgents;
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			foreach (var item in query)
			{
				context.DeliveryAgents.Remove(item);
			}
			return context.SaveChanges() > 0;
		}

		// GET api/deliveryagent/count
		[HttpGet]
		public int Count(string filter = null)
		{
			IQueryable<DeliveryAgent> query = context.DeliveryAgents.AsNoTracking();
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			return query.Count();
		}

		// GET api/deliveryagent/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.DeliveryAgents.AsNoTracking()
				.Where(queryBuider.CreateWhere(filter))
				.Select(x=> new
				{
					Id=x.Id,
					Name=x.Name,
					Account=x.Account,
					DeliveryDiscount=x.DeliveryDiscount,
					UpdateDate=x.UpdateDate,
					CreateDate=x.CreateDate,
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
