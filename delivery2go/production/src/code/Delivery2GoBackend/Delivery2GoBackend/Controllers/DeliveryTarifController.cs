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
	public class DeliveryTarifController : ApiController
	{

		deliveryContext context;
		QueryBuilder<DeliveryTarif> queryBuider;

		public DeliveryTarifController()
		{
			context = new deliveryContext();
			queryBuider = new QueryBuilder<DeliveryTarif>()
				.MapMember("DeliveryAgentName", x => x.DeliveryAgent.Name)
				.MapMember("UpdateUserName", x => x.UpdateUser.Name);
		}

		// GET api/deliverytarif/get/
		public System.Collections.ICollection Get(string filter = null, string orderby = null, int top = -1, int skip = -1)
		{
			if (orderby == null)
			{
				orderby = "Id";
			}
			return queryBuider.CreateQuery(context.DeliveryTarifs.AsNoTracking(), filter, orderby, skip, top, x => new
				{
					Id=x.Id,
					FromMiles=x.FromMiles,
					ToMiles=x.ToMiles,
					Price=x.Price,
					DeliveryAgentId=x.DeliveryAgentId,
					DeliveryAgentName=x.DeliveryAgent.Name,
					UpdateDate=x.UpdateDate,
					CreateDate=x.CreateDate,
					UpdateUserId=x.UpdateUserId,
					UpdateUserName=x.UpdateUser.Name,
				}).ToList();
		}

		// GET api/deliverytarif/get/5
		public object Get(int id)
		{
			return context.DeliveryTarifs.AsNoTracking()
				.Where(x=>x.Id == id)
				.Select(x=> new
				{
					Id=x.Id,
					FromMiles=x.FromMiles,
					ToMiles=x.ToMiles,
					Price=x.Price,
					DeliveryAgentId=x.DeliveryAgentId,
					DeliveryAgentName=x.DeliveryAgent.Name,
					UpdateDate=x.UpdateDate,
					CreateDate=x.CreateDate,
					UpdateUserId=x.UpdateUserId,
					UpdateUserName=x.UpdateUser.Name,
				})
			.FirstOrDefault();
		}

		// POST api/deliverytarif/
		public HttpResponseMessage Post([FromBody]DeliveryTarif value)
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

		// PUT api/deliverytarif/
		public HttpResponseMessage Put([FromBody]DeliveryTarif value, string filter = null)
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
				var old = context.DeliveryTarifs.SingleOrDefault(queryBuider.CreateWhere(filter));
				old.Id = value.Id;
				old.FromMiles = value.FromMiles;
				old.ToMiles = value.ToMiles;
				old.Price = value.Price;
				old.DeliveryAgentId = value.DeliveryAgentId;
				old.UpdateDate = value.UpdateDate;
				old.CreateDate = value.CreateDate;
				old.UpdateUserId = value.UpdateUserId;
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			var result = context.SaveChanges() > 0;
			return Request.CreateResponse(HttpStatusCode.OK, result);
		}

		// DELETE api/deliverytarif/
		public bool Delete(string filter = null)
		{
			IQueryable<DeliveryTarif> query = context.DeliveryTarifs;
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			foreach (var item in query)
			{
				context.DeliveryTarifs.Remove(item);
			}
			return context.SaveChanges() > 0;
		}

		// GET api/deliverytarif/count
		[HttpGet]
		public int Count(string filter = null)
		{
			IQueryable<DeliveryTarif> query = context.DeliveryTarifs.AsNoTracking();
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			return query.Count();
		}

		// GET api/deliverytarif/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.DeliveryTarifs.AsNoTracking()
				.Where(queryBuider.CreateWhere(filter))
				.Select(x=> new
				{
					Id=x.Id,
					FromMiles=x.FromMiles,
					ToMiles=x.ToMiles,
					Price=x.Price,
					DeliveryAgentId=x.DeliveryAgentId,
					DeliveryAgentName=x.DeliveryAgent.Name,
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
