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
	public class PermissionController : ApiController
	{

		deliveryContext context;
		QueryBuilder<Permission> queryBuider;

		public PermissionController()
		{
			context = new deliveryContext();
			queryBuider = new QueryBuilder<Permission>()
				.MapMember("RollName", x => x.Roll.Name)
				.MapMember("UserName", x => x.User.Name);
		}

		// GET api/permission/get/
		public System.Collections.ICollection Get(string filter = null, string orderby = null, int top = -1, int skip = -1)
		{
			if (orderby == null)
			{
				orderby = "Id";
			}
			return queryBuider.CreateQuery(context.Permissions.AsNoTracking(), filter, orderby, skip, top, x => new
				{
					Id=x.Id,
					RollId=x.RollId,
					RollName=x.Roll.Name,
					UserId=x.UserId,
					UserName=x.User.Name,
					CanRead=x.CanRead,
					CanWrite=x.CanWrite,
					CanCreate=x.CanCreate,
					CanDelete=x.CanDelete,
					CreateDate=x.CreateDate,
					UpdateDate=x.UpdateDate,
				}).ToList();
		}

		// GET api/permission/get/5
		public object Get(int id)
		{
			return context.Permissions.AsNoTracking()
				.Where(x=>x.Id == id)
				.Select(x=> new
				{
					Id=x.Id,
					RollId=x.RollId,
					RollName=x.Roll.Name,
					UserId=x.UserId,
					UserName=x.User.Name,
					CanRead=x.CanRead,
					CanWrite=x.CanWrite,
					CanCreate=x.CanCreate,
					CanDelete=x.CanDelete,
					CreateDate=x.CreateDate,
					UpdateDate=x.UpdateDate,
				})
			.FirstOrDefault();
		}

		// POST api/permission/
		public HttpResponseMessage Post([FromBody]Permission value)
		{
			ServerValidationInfo vi = null;
			value.CreateDate=DateTime.Now;
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

		// PUT api/permission/
		public HttpResponseMessage Put([FromBody]Permission value, string filter = null)
		{
			ServerValidationInfo vi = null;
			value.CreateDate=DateTime.Now;
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
				var old = context.Permissions.SingleOrDefault(queryBuider.CreateWhere(filter));
				old.Id = value.Id;
				old.RollId = value.RollId;
				old.UserId = value.UserId;
				old.CanRead = value.CanRead;
				old.CanWrite = value.CanWrite;
				old.CanCreate = value.CanCreate;
				old.CanDelete = value.CanDelete;
				old.CreateDate = value.CreateDate;
				old.UpdateDate = value.UpdateDate;
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			var result = context.SaveChanges() > 0;
			return Request.CreateResponse(HttpStatusCode.OK, result);
		}

		// DELETE api/permission/
		public bool Delete(string filter = null)
		{
			IQueryable<Permission> query = context.Permissions;
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			foreach (var item in query)
			{
				context.Permissions.Remove(item);
			}
			return context.SaveChanges() > 0;
		}

		// GET api/permission/count
		[HttpGet]
		public int Count(string filter = null)
		{
			IQueryable<Permission> query = context.Permissions.AsNoTracking();
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			return query.Count();
		}

		// GET api/permission/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.Permissions.AsNoTracking()
				.Where(queryBuider.CreateWhere(filter))
				.Select(x=> new
				{
					Id=x.Id,
					RollId=x.RollId,
					RollName=x.Roll.Name,
					UserId=x.UserId,
					UserName=x.User.Name,
					CanRead=x.CanRead,
					CanWrite=x.CanWrite,
					CanCreate=x.CanCreate,
					CanDelete=x.CanDelete,
					CreateDate=x.CreateDate,
					UpdateDate=x.UpdateDate,
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
