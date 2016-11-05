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
	public class OrderTypeController : ApiController
	{

		deliveryContext context;
		QueryBuilder<OrderType> queryBuider;

		public OrderTypeController()
		{
			context = new deliveryContext();
			queryBuider = new QueryBuilder<OrderType>();
		}

		// GET api/ordertype/get/
		public System.Collections.ICollection Get(string filter = null, string orderby = null, int top = -1, int skip = -1)
		{
			if (orderby == null)
			{
				orderby = "Id";
			}
			return queryBuider.CreateQuery(context.OrderTypes.AsNoTracking(), filter, orderby, skip, top).ToList();
		}

		// GET api/ordertype/get/5
		public object Get(int id)
		{
			return context.OrderTypes.AsNoTracking()
				.Where(x=>x.Id == id)
			.FirstOrDefault();
		}

		// POST api/ordertype/
		public HttpResponseMessage Post([FromBody]OrderType value)
		{
			ServerValidationInfo vi = null;
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

		// PUT api/ordertype/
		public HttpResponseMessage Put([FromBody]OrderType value, string filter = null)
		{
			ServerValidationInfo vi = null;
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
				var old = context.OrderTypes.SingleOrDefault(queryBuider.CreateWhere(filter));
				old.Id = value.Id;
				old.Code = value.Code;
				old.Name = value.Name;
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			var result = context.SaveChanges() > 0;
			return Request.CreateResponse(HttpStatusCode.OK, result);
		}

		// DELETE api/ordertype/
		public bool Delete(string filter = null)
		{
			IQueryable<OrderType> query = context.OrderTypes;
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			foreach (var item in query)
			{
				context.OrderTypes.Remove(item);
			}
			return context.SaveChanges() > 0;
		}

		// GET api/ordertype/count
		[HttpGet]
		public int Count(string filter = null)
		{
			IQueryable<OrderType> query = context.OrderTypes.AsNoTracking();
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			return query.Count();
		}

		// GET api/ordertype/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.OrderTypes.AsNoTracking()
				.Where(queryBuider.CreateWhere(filter))
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
